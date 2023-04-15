package com.sahab.services;

import com.sahab.configs.PropertyReader;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.Arrays;
import java.util.Properties;

public class KafkaConsumerService {

    String  bootstrapServers= PropertyReader.readProperty("config.properties","kafkaBootstrapServer");
    String grp_id="third_app";
    String topic=PropertyReader.readProperty("config.properties","topic");
    //Creating consumer properties
    Properties properties=new Properties();

    {
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, grp_id);
        properties.setProperty(ConsumerConfig.ISOLATION_LEVEL_CONFIG, "read_committed");
        properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
    }
    //creating consumer
    private KafkaConsumer<String,String> consumer= new KafkaConsumer<String,String>(properties);

    public  KafkaConsumer<String, String> getConsumer() {
        consumer.subscribe(Arrays.asList( topic));
        return consumer;
    }



}
