package com.sahab.services;
import com.sahab.configs.PropertyReader;
import org.junit.Before;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.assertTrue;

public class LogServiceTest {
    Path logDirectory = Paths.get(PropertyReader.readProperty("config.properties","logDirectory"));

    @Before
    void setup(){
        
    }
    @Test
    void watchLogDirectory() {
    }

    @Test
    void sendAndDeleteLogFiles() {
    }
}
