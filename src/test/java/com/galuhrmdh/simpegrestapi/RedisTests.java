package com.galuhrmdh.simpegrestapi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.galuhrmdh.simpegrestapi.model.JobMessage;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@SpringBootTest
public class RedisTests {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Test
    void setRedisValue() throws JsonProcessingException {
        ValueOperations<String, String> operations = redisTemplate.opsForValue();

        JobMessage customMessage = new JobMessage("aa", "bb", "cc");

        ObjectWriter ow = new ObjectMapper().writer();
        String json = ow.writeValueAsString(customMessage);

        operations.set("CACHE_EXPORTT", json);

        Assert.notNull(operations.get("CACHE_EXPORTT"), "value must not be null");
    }

    @Test
    void getRedisValue() throws JsonProcessingException {
        ValueOperations<String, String> operations = redisTemplate.opsForValue();

        ObjectMapper objectMapper = new ObjectMapper();
        JobMessage customMessage = objectMapper.readValue(operations.get("CACHE_EXPORTT"), JobMessage.class);

        LocalDateTime myDateObj = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_ss");
        String formattedDate = myDateObj.format(myFormatObj);

        log.info("FILENAMEE {}", formattedDate.concat(".csv"));
    }
}
