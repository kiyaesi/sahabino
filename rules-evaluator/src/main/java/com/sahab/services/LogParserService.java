package com.sahab.services;

public class LogParserService {

    public String logFileDate(String logName){
        return logName.split("-")[1]+"T"+(logName.split("-")[2].split("\\.")[0]);
    }

    public String logDate(String logName){
        return logName.split("\\s+")[0];
    }
    public String logDateTime(String logName){
        return logName.split("\\s+")[0]+"T"+logName.split("\\s+")[1].split(",")[0];
    }

    public String warningType(String logName){
        return logName.split("\\s+")[1];
    }

    public String logMassage(String logName){
        return logName.split("\\s+-\\s+")[1];
    }

    public String systemName(String logName){
        return logName.split("-")[0];
    }
}
