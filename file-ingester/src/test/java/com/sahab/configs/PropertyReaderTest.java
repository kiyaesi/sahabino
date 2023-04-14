package com.sahab.configs;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;

class PropertyReaderTest {

        private PropertyReader propertyReader;

        @BeforeEach
        public void setUp() throws IOException {
            propertyReader = new PropertyReader();

        }

        @Test
        public void testReadProperty() {
            // Call the readProperty method
            String value = propertyReader.readProperty("test.properties", "key1");

            // Verify that the mock objects were used as expected
            assertEquals("test", value);

}}