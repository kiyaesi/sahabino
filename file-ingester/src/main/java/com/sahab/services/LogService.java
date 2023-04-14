package com.sahab.services;

import com.sahab.configs.PropertyReader;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.*;

public class LogService {
    private KafkaProducerService kafkaProducerService;
    private KafkaProducer kafkaProducer;
    public LogService(KafkaProducerService kafkaProducerService){
        this.kafkaProducerService = kafkaProducerService;
         kafkaProducer = kafkaProducerService.getKafkaProducer();

    }
    public   void watchLogDirectory(Path logDir)throws IOException {
        kafkaProducer.initTransactions();
        WatchService watcher = FileSystems.getDefault().newWatchService();
        logDir.register(watcher, StandardWatchEventKinds.ENTRY_MODIFY,StandardWatchEventKinds.ENTRY_CREATE);
        try {
            if(logDir.toFile().listFiles().length!=0){
                sendAndDeleteLogFiles( logDir.toFile().listFiles(), kafkaProducer);
            }
            while (true) {
                WatchKey key = watcher.take();
                for (WatchEvent<?> event : key.pollEvents()) {
                    if (event.kind() == StandardWatchEventKinds.OVERFLOW) {
                        continue;
                    }
                    else{
                        sendAndDeleteLogFiles( logDir.toFile().listFiles(), kafkaProducer);
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

    public  void sendAndDeleteLogFiles(File[] logFiles,KafkaProducer kafkaProducer){

        BufferedReader br = null;
        for(File file : logFiles){
            try {

                String sCurrentLine;
                String key = file.getName();

                br = new BufferedReader(new FileReader(file.getCanonicalPath()));
                if (br != null){
                    kafkaProducer.beginTransaction();
                    while ((sCurrentLine = br.readLine()) != null) {
                        String topic = PropertyReader.readProperty("config.properties","topic");
                        ProducerRecord<String, String> producerRecord = new ProducerRecord<>(topic,key,sCurrentLine);
                        kafkaProducer.send(producerRecord);
                    }

                    br.close();
                }

                kafkaProducer.flush();
                file.delete();
                kafkaProducer.commitTransaction();

            } catch (Exception e) {
                e.printStackTrace();
kafkaProducer.abortTransaction();
            }

        }


    }

}
