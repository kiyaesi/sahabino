package com.sahab.configs;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Properties;

public class PropertyReader {

   public static String  readProperty(String fileUrl,String key){
       String value;
       try {
            java.net.URL configFileUrl = ClassLoader.getSystemResource(fileUrl);

            Properties props = new Properties();
            props.load(configFileUrl.openStream());
            value= props.getProperty(key);
            return value;

       } catch (FileNotFoundException ex) {
           // file does not exist

       } catch (Exception ex) {
           // I/O error
       }
   return null;
   }
}
