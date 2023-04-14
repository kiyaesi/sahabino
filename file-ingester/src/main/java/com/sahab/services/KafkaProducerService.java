package com.sahab.services;

import com.sahab.configs.PropertyReader;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class KafkaProducerService {
    private   String bootstrapServers ;
    private  KafkaProducer<String, String> kafkaProducer;
     private  PropertyReader propertyReader = new PropertyReader();
    {
        bootstrapServers=propertyReader.readProperty("config.properties","kafkaBootstrapServer");
    }


    public  KafkaProducer<String, String>  getKafkaProducer(){
        Properties kafkaProducerProperties = new Properties();
        kafkaProducerProperties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        kafkaProducerProperties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        kafkaProducerProperties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        kafkaProducerProperties.put(ProducerConfig.TRANSACTIONAL_ID_CONFIG, "log-transaction-id");
        kafkaProducerProperties.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, true);
        kafkaProducer = new KafkaProducer<>(kafkaProducerProperties);
       return kafkaProducer  ;
    }

}
