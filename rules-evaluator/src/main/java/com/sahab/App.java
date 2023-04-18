package com.sahab;

import com.sahab.Entity.LogWarningsEntity;
import com.sahab.Repository.LogWarningsRepositroy;
import com.sahab.configs.PropertyReader;
import com.sahab.services.KafkaConsumerService;
import com.sahab.services.LogParserService;
import com.sahab.services.RulesEvaluatorService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
@SpringBootApplication
public class App implements CommandLineRunner {
    private static KafkaConsumerService kafkaConsumerService = new KafkaConsumerService();
    private final static int SERVER_WARNING_EXCEED_NUMBER = Integer.parseInt(PropertyReader.readProperty("Rules.properties","checkServerWarningsExceed.time"));
    private static int SYSTEM_EXCEED_NUMBER = Integer.parseInt(PropertyReader.readProperty("Rules.properties","checkServerSystemExceed.time"));
    Pageable pageForSystemLogs = PageRequest.of(0, SYSTEM_EXCEED_NUMBER);
    Pageable pageForLogsType = PageRequest.of(0, SERVER_WARNING_EXCEED_NUMBER);
    private static LogParserService logParserService = new LogParserService();
    private static KafkaConsumer kafkaConsumer = kafkaConsumerService.getConsumer();
    private static RulesEvaluatorService rulesEvaluatorService = new RulesEvaluatorService();

    private final LogWarningsRepositroy logWarningsRepositroy ;

    public App(LogWarningsRepositroy logWarningsRepositroy) {
        this.logWarningsRepositroy = logWarningsRepositroy;
    }

    public static void main(String args[]){
        SpringApplication.run(App.class, args);
    }
    public void run(String... arg0) throws Exception {

        while(true){

            ConsumerRecords<String,String> records=kafkaConsumer.poll(Duration.ofMillis(100));
            for(ConsumerRecord<String,String> record: records){
                if(rulesEvaluatorService.checkLogTypeRule(logParserService.warningType(record.value()))){
                    LogWarningsEntity receivedLog  =  LogWarningsEntity.builder()
                            .warningName(logParserService.warningType(record.value()))
                            .warningType(logParserService.warningType(record.value()))
                            .description(logParserService.logMassage(record.value()))
                            .logCreatedAt(LocalDateTime.parse( logParserService.logDateTime(record.value())))
                            .system(logParserService.systemName(record.key()))
                            .build();

                    logWarningsRepositroy.save(receivedLog);
                    if(rulesEvaluatorService.shouldCheckServerWarningsExceed(receivedLog)){
                    List<LogWarningsEntity> logWarningsEntities = logWarningsRepositroy.findLogWarningsByWarningTypeSortByTime(receivedLog.getWarningType(),receivedLog.getSystem(),pageForLogsType);
                    if(rulesEvaluatorService.checkServerWarningsExceed(logWarningsEntities)){
                        LogWarningsEntity receivedLogSecondRoleChecked  =  LogWarningsEntity.builder()
                                .warningName("SYSTEM_SPECEFIC_WARNING")
                                .warningType(null)
                                .description(receivedLog.getWarningType()+"|"+logWarningsEntities.get(0).getDescription()+"|"+logWarningsEntities.get(1).getDescription()+"|"
                                        +rulesEvaluatorService.warningLogRate(logWarningsEntities))
                                .logCreatedAt(LocalDateTime.parse( logParserService.logDateTime(record.value())))
                                .system(logParserService.systemName(record.key()))
                                .build();
                        logWarningsRepositroy.save(receivedLogSecondRoleChecked);

                    }}
                    if(rulesEvaluatorService.shouldCheckSystemExceed(receivedLog)){
                        List logWarningsEntities = logWarningsRepositroy.findLogWarningsBySystemSortByTime(receivedLog.getSystem(), pageForSystemLogs);
                    if(rulesEvaluatorService.checkSystemWarningsExceed(logWarningsEntities)){
                        LogWarningsEntity receivedLogThirdRoleChecked  = LogWarningsEntity.builder()
                                .warningName("SYSTEM_WARNING")
                                .warningType(null)
                                .description(rulesEvaluatorService.systemLogRate(logWarningsEntities))
                                .logCreatedAt(LocalDateTime.parse( logParserService.logDateTime(record.value())))
                                .system(logParserService.systemName(record.key()))
                                .build();

                        logWarningsRepositroy.save(receivedLogThirdRoleChecked);
                    }}

                }
            }


        }





    }
}
