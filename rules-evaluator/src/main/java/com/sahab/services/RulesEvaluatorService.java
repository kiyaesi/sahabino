package com.sahab.services;

import com.sahab.Entity.LogWarningsEntity;
import com.sahab.configs.PropertyReader;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class RulesEvaluatorService {
    private  static HashMap<String, LocalDateTime> systemsError;
    private  static HashMap<String, LocalDateTime> warningError;
    private  static final List<String> WARNING_TYPES = Arrays.asList(PropertyReader.readProperty("Rules.properties","warningTypes").toLowerCase().split("\\s*,\\s*"));
     private final static int SYSTEM_EXCEED_TIME = Integer.parseInt(PropertyReader.readProperty("Rules.properties","checkServerWarningsExceed.time"));
     private final static int SERVER_WARNINGS_EXCEED_TIME = Integer.parseInt(PropertyReader.readProperty("Rules.properties","checkServerWarningsExceed.time"));

    private final static int SERVER_WARNING_EXCEED_NUMBER = Integer.parseInt(PropertyReader.readProperty("Rules.properties","checkServerWarningsExceed.time"));
    private final static int SYSTEM_EXCEED_NUMBER = Integer.parseInt(PropertyReader.readProperty("Rules.properties","checkServerWarningsExceed.time"));
        //Implement first rule
    public boolean checkLogTypeRule(String logType){
         if(WARNING_TYPES.contains(logType.toLowerCase())){
             return true;
         }
         else return false;
    }

    private boolean checkHashMapTime(HashMap<String,LocalDateTime> map,String name, int time){
        if(map.containsKey(name)){
            Duration dur = Duration.between(LocalDateTime.now().minusSeconds(time), map.get(name));
            if(dur.isNegative()){
                return false;
            }
            map.remove(name);
        }
        return true;
    }

    //Check time before checking the second rule
    public boolean shouldCheckServerWarningsExceed(LogWarningsEntity logWarningsEntity){
        String name =logWarningsEntity.getSystem()+logWarningsEntity.getWarningType();
      return  checkHashMapTime(warningError,name,SERVER_WARNINGS_EXCEED_TIME);
    }
    //Check time before checking the third rule
    public boolean shouldCheckSystemExceed(LogWarningsEntity logWarningsEntity){
        String name =logWarningsEntity.getSystem();
        return  checkHashMapTime(systemsError,name,SYSTEM_EXCEED_TIME);

    }

    //Check the second rule
    public boolean checkServerWarningsExceed(List<LogWarningsEntity> logWarningsEntities){

        if(logWarningsEntities.size()<SERVER_WARNING_EXCEED_NUMBER){
            return false;

        }

        else if (logWarningsEntities.get(0).getLogCreatedAt().minusSeconds(SERVER_WARNINGS_EXCEED_TIME).isAfter(logWarningsEntities.get((logWarningsEntities.size()-1)).getLogCreatedAt()))
        {
            return false;
        }
        else if (logWarningsEntities.get(0).getLogCreatedAt().minusSeconds(SERVER_WARNINGS_EXCEED_TIME).isBefore(logWarningsEntities.get((logWarningsEntities.size()-1)).getLogCreatedAt()))
        {   String name =logWarningsEntities.get(0).getSystem()+logWarningsEntities.get(0).getWarningType();

            if(warningError.containsKey(name)){
                Duration dur = Duration.between(LocalDateTime.now().minusSeconds(180), warningError.get(name));
                if(dur.isNegative()){
                    return false;}

            }
            }
        String name =logWarningsEntities.get(0).getSystem()+logWarningsEntities.get(0).getWarningType();
        warningError.put(name,LocalDateTime.now());
            return true;
    }
    public boolean checkSystemWarningsExceed(List<LogWarningsEntity> logWarningsEntities){
        if(logWarningsEntities.size()<SYSTEM_EXCEED_NUMBER){
            return false;

        }
        else if (logWarningsEntities.get(0).getLogCreatedAt().minusSeconds(SYSTEM_EXCEED_TIME).isAfter(logWarningsEntities.get((logWarningsEntities.size()-1)).getLogCreatedAt()))
        {
            return false;
        }
        String name =logWarningsEntities.get(0).getSystem();
        systemsError.put(name,LocalDateTime.now());
        return true;
    }

    public String warningLogRate(List<LogWarningsEntity> logWarningsEntities){
        long durationSecond = Duration.between(logWarningsEntities.get(0).getLogCreatedAt(), logWarningsEntities.get(logWarningsEntities.size()-1).getLogCreatedAt()).toSeconds();
        Long rate = (durationSecond/SERVER_WARNING_EXCEED_NUMBER);

        return rate.toString();
    }
    public String systemLogRate(List<LogWarningsEntity> logWarningsEntities){
        long durationSecond = Duration.between(logWarningsEntities.get(0).getLogCreatedAt(), logWarningsEntities.get(logWarningsEntities.size()-1).getLogCreatedAt()).toSeconds();
        Long rate = (durationSecond/SYSTEM_EXCEED_NUMBER);

        return rate.toString();
    }


}
