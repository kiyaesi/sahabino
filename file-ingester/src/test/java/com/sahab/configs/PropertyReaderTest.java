//package com.sahab.configs;
//
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.Test;
//
//import java.io.IOException;
//
//class PropertyReaderTest {
//
//        private PropertyReader propertyReader;
//
//        @Before
//        public void setUp() throws IOException {
//            propertyReader = new PropertyReader();
//
//        }
//
//        @Test
//        public void testReadProperty() {
//            // Call the readProperty method
//            String value = propertyReader.readProperty("test.properties", "key1");
//
//            // Verify that the mock objects were used as expected
//            Assert.assertEquals("test", value);
//        }
//}