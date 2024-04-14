package com.galuhrmdh.simpegrestapi;

import com.galuhrmdh.simpegrestapi.enums.NotificationType;
import com.galuhrmdh.simpegrestapi.enums.RabbitJobType;
import com.galuhrmdh.simpegrestapi.enums.RedisKey;
import com.galuhrmdh.simpegrestapi.model.JobMessage;
import com.galuhrmdh.simpegrestapi.model.notification.CreateNotificationRequest;
import com.galuhrmdh.simpegrestapi.service.ExportService;
import com.galuhrmdh.simpegrestapi.service.NotificationService;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@Component
public class JobProcessor {

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private ExportService exportService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    private ValueOperations<String, String> redisOperations;

    @PostConstruct
    public void postConstruct() {
        redisOperations = redisTemplate.opsForValue();
    }

    public void processingJob(JobMessage jobMessage) {
        switch (jobMessage.getType()) {
            case RabbitJobType.EXPORT_EMPLOYEE -> this.exportEmployee();
            case RabbitJobType.EXPORT_WARNING_REPORT -> this.exportWarningReport();
            default -> throw new IllegalStateException("Unexpected Job: " + jobMessage.getType());
        }
    }

    private void createNotification(NotificationType notificationType, String fileName) {
        if (fileName == null) return;

        CreateNotificationRequest createNotificationRequest = new CreateNotificationRequest();
        createNotificationRequest.setNotificationType(notificationType);
        createNotificationRequest.setAttachment(fileName);

        notificationService.create(createNotificationRequest);
    }

    public void exportEmployee() {
        CountDownLatch latch = new CountDownLatch(5);

        redisOperations.set(RedisKey.EXPORT_EMPLOYEE_PROGRESS.toString(), "1");

        rabbitTemplate.convertAndSend(RabbitMQUtil.topicExchangeName, String.format("simpeg.rkey.%s", RabbitMQUtil.notificationQueue), RabbitMQUtil.notificationQueue);

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        String fileName = exportService.exportEmployee();

        redisTemplate.delete(RedisKey.EXPORT_EMPLOYEE_PROGRESS.toString());
        log.info("PROSES EXPORT EMPLOYEE TELAH SELESAI");

        this.createNotification(NotificationType.EE, fileName);

        rabbitTemplate.convertAndSend(RabbitMQUtil.topicExchangeName, String.format("simpeg.rkey.%s", RabbitMQUtil.notificationQueue), RabbitMQUtil.notificationQueue);
    }

    public void exportWarningReport() {
        CountDownLatch latch = new CountDownLatch(5);

        for (var i = 0; i < 5; i++) {
            redisOperations.set(RedisKey.EXPORT_WARNING_REPORT_PROGRESS.toString(), Integer.toString(20 * i));
            log.info("EXPORT REPORT SEDANG DI PROSES {}", i);
            rabbitTemplate.convertAndSend(RabbitMQUtil.topicExchangeName, String.format("simpeg.rkey.%s", RabbitMQUtil.notificationQueue), RabbitMQUtil.notificationQueue);
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            latch.countDown();
        }

        try {
            latch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        redisTemplate.delete(RedisKey.EXPORT_WARNING_REPORT_PROGRESS.toString());
        log.info("PROSES EXPORT REPORT TELAH SELESAI");
    }

}
