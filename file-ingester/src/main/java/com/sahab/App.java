package com.sahab;


import com.sahab.configs.PropertyReader;
import com.sahab.services.KafkaProducerService;
import com.sahab.services.LogService;

import java.io.IOException;
import java.nio.file.*;

public class App
{

   public static void main( String[] args ) throws IOException {
       Path logDirectory = Paths.get(PropertyReader.readProperty("config.properties","logDirectory"));

       KafkaProducerService kafkaProducerService = new KafkaProducerService();
       LogService logService = new LogService(kafkaProducerService);
       logService.watchLogDirectory(logDirectory );
    }


}
