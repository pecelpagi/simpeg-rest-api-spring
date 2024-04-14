package com.galuhrmdh.simpegrestapi.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.galuhrmdh.simpegrestapi.RabbitMQUtil;
import com.galuhrmdh.simpegrestapi.enums.RabbitJobType;
import com.galuhrmdh.simpegrestapi.model.JobMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RabbitPublisherService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public String toJobMessageData(RabbitJobType rabbitJobType) throws JsonProcessingException {
        final var jobMessage = new JobMessage(rabbitJobType);

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();

        return ow.writeValueAsString(jobMessage);
    }

    public void exportEmployee() throws JsonProcessingException {
        rabbitTemplate.convertAndSend(RabbitMQUtil.topicExchangeName, String.format("simpeg.rkey.%s", RabbitMQUtil.exportEmployeeQueue), this.toJobMessageData(RabbitJobType.EXPORT_EMPLOYEE));
    }

    public void exportWarningReport() throws JsonProcessingException {
        rabbitTemplate.convertAndSend(RabbitMQUtil.topicExchangeName, String.format("simpeg.rkey.%s", RabbitMQUtil.exportWarningReportQueue), this.toJobMessageData(RabbitJobType.EXPORT_WARNING_REPORT));
    }

}
