package com.galuhrmdh.simpegrestapi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.galuhrmdh.simpegrestapi.model.JobMessage;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@Component
public class Receiver {

    @Autowired
    public JobProcessor jobProcessor;

    private ExecutorService executorService;

    @PostConstruct
    public void postConstruct() {
        executorService = Executors.newFixedThreadPool(2);
    }

    @PreDestroy
    public void preDestroy() {
        executorService.shutdown();
    }

    public void receiveMessage(String message) {
        executorService.execute(() -> {
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                JobMessage jobMessage = objectMapper.readValue(message, JobMessage.class);
                jobProcessor.processingJob(jobMessage);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        });
    }

}
