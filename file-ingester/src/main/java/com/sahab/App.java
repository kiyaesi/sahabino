package com.sahab;


import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.*;
import java.time.Duration;
import java.util.Properties;

public class App
{

   public static void main( String[] args ) throws IOException {
       Path logDirectory = Paths.get("/home/kiyarash/workspace/sahab/sahabino/file-ingester/src/main/java/com/sahab/logs");
       String bootstrapServers = "127.0.0.1:9092";
       Properties properties = new Properties();
       properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
       properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
       properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
       properties.put(ProducerConfig.TRANSACTIONAL_ID_CONFIG, "log-transaction-id");
       properties.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, true);
//     create the producer
       KafkaProducer<String, String>  kafkaProducer = new KafkaProducer<>(properties);
       kafkaProducer.initTransactions();

       // create a producer record

       App.watchLogDirectory(logDirectory , kafkaProducer);
        System.out.println( "Hello World!" );
    }
    public  static void watchLogDirectory(Path logDir,KafkaProducer kafkaProducer)throws IOException {
        WatchService watcher = FileSystems.getDefault().newWatchService();



        logDir.register(watcher, StandardWatchEventKinds.ENTRY_MODIFY,StandardWatchEventKinds.ENTRY_CREATE);
        try {
            while (true) {
                WatchKey key = watcher.take();
                // Handling file change events：
                // key.pollEvents()It is used to obtain file change events,
                // which can only be obtained once and cannot be obtained repeatedly,
                // similar to the form of a queue.
                for (WatchEvent<?> event : key.pollEvents()) {
                    // event.kind()：event type
                    System.out.println("found");
                    if (event.kind() == StandardWatchEventKinds.OVERFLOW) {
                        continue;
                    }
                    else{
                        readAndDeleteLogFiles( logDir.toFile().listFiles(), kafkaProducer);
                          }
                }
                // This method needs to be reset every time the take() or poll() method of WatchService is called
                if (!key.reset()) {
                    break;
                }
            }
            kafkaProducer.close();
        } catch (Exception e) {
            e.printStackTrace();
            kafkaProducer.close();

        }
    }


    public static void readAndDeleteLogFiles(File[] fileList, KafkaProducer kafkaProducer){
        BufferedReader br = null;
        for(File file : fileList){
                try {

                    String sCurrentLine;

                    br = new BufferedReader(new FileReader(file.getCanonicalPath()));
                    if (br != null){
                    kafkaProducer.beginTransaction();
                        while ((sCurrentLine = br.readLine()) != null) {
                            System.out.println(sCurrentLine);
                            ProducerRecord<String, String> producerRecord =
                                    new ProducerRecord<>("logs", sCurrentLine);
                            kafkaProducer.send(producerRecord);
                        }
                        br.close();
                    }

                    kafkaProducer.flush();
                    file.delete();
                    kafkaProducer.commitTransaction();

                } catch (IOException e) {
                    e.printStackTrace();
                    kafkaProducer.close();

                }

            }


    }

}
