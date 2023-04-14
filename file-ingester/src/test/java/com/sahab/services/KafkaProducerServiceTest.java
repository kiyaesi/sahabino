package com.sahab.services;

import com.sahab.configs.PropertyReader;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
class KafkaProducerServiceTest {
    private KafkaProducerService kafkaProducerService;
    private String bootstrapServers= PropertyReader.readProperty("config.properties","kafkaBootstrapServer");
    @BeforeEach
    public void setUp() {
        kafkaProducerService = new KafkaProducerService();

    }
    @Test
    void getKafkaProducer() {
        KafkaProducer<String, String> mockProducer =kafkaProducerService.getKafkaProducer();

        verify(mockProducer).initTransactions();
        verify(mockProducer).send(any());

    }
}
    }
}