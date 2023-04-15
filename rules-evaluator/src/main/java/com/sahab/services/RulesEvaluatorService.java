package com.sahab.services;

import com.sahab.Entity.LogWarningsEntity;
import com.sahab.configs.PropertyReader;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

public class RulesEvaluatorService {
     private  static final List<String> WARNING_TYPES = Arrays.asList(PropertyReader.readProperty("Rules.properties","warningTypes").toLowerCase().split("\\s*,\\s*"));
     private static int SystemExceedTime = Integer.parseInt(PropertyReader.readProperty("Rules.properties","checkServerWarningsExceed.time"));
     private static int ServerWarningsExceedTime = Integer.parseInt(PropertyReader.readProperty("Rules.properties","checkServerWarningsExceed.time"));
    private static int checkServerWarningsExceedTime = Integer.parseInt(PropertyReader.readProperty("Rules.properties","checkServerWarningsExceed.time"));

    private final static int SERVER_WARNING_EXCEED_NUMBER = Integer.parseInt(PropertyReader.readProperty("Rules.properties","checkServerWarningsExceed.time"));
    private static int SYSTEM_EXCEED_NUMBER = Integer.parseInt(PropertyReader.readProperty("Rules.properties","checkServerWarningsExceed.time"));

    public boolean checkLogTypeRule(String logType){
         if(WARNING_TYPES.contains(logType.toLowerCase())){
             return true;
         }
         else return false;
    }
    public boolean checkServerWarningsExceed(List<LogWarningsEntity> logWarningsEntities){
        if(logWarningsEntities.size()<SERVER_WARNING_EXCEED_NUMBER){
            return false;

        }
        else if (logWarningsEntities.get(0).getLogCreatedAt().minusSeconds(ServerWarningsExceedTime).isAfter(logWarningsEntities.get((logWarningsEntities.size()-1)).getLogCreatedAt()))
        {
            return false;
        }
            return true;
    }
    public boolean checkSystemWarningsExceed(List<LogWarningsEntity> logWarningsEntities){
        if(logWarningsEntities.size()<SYSTEM_EXCEED_NUMBER){
            return false;

        }
        else if (logWarningsEntities.get(0).getLogCreatedAt().minusSeconds(SystemExceedTime).isAfter(logWarningsEntities.get((logWarningsEntities.size()-1)).getLogCreatedAt()))
        {
            return false;
        }
        return true;
    }


}
