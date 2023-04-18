package com.sahab.services;

import org.junit.BeforeClass;
import org.junit.Test;
import com.sahab.services.LogParserService;
import static org.junit.Assert.*;

public class LogParserServiceTest {

    static String log ;
    static LogParserService logParserService;
    static String fileName ;
    @BeforeClass
    public static void setup(){

        log= "2021-7-12 01:22:42,114 [ThreadName] Warning com.sahab.fileingester - salam rezam  injam ";
        fileName = "component_name-2021_07_12-01_55_55.log";
        logParserService = new LogParserService();
    }

    @Test
    public void logFileDateTest() {

    assertEquals(logParserService.logFileDate(fileName),"2021_07_12T01_55_55");
    }

    @Test
    public void logDateTest() {
    assertEquals(logParserService.logDate(log),"2021-7-12");
    }

    @Test
    public void logDateTimeTest() {
        assertEquals(logParserService.logDateTime(log),"2021-7-12T01:22:42");
    }

    @Test
    public void logMassageTest() {
        assertEquals(logParserService.logMassage(log),"salam rezam  injam ");

    }

    @Test
    public void systemNameTest() {
        assertEquals(logParserService.systemName(fileName),"component_name");
    }
}