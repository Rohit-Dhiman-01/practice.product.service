package com.ecommerce.product.service.config.redisConfigs;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class RedisService {
    private final RedisTemplate redisTemplate;

    private static final ObjectMapper mapper = new ObjectMapper()
            .registerModule(new com.fasterxml.jackson.datatype.jsr310.JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    public <T>T get(String key, Class<T> tClass){
        try {
        Object object = redisTemplate.opsForValue().get(key);
            if (object == null) {
                return null;
            }
            return mapper.readValue(object.toString() , tClass);
        } catch (JsonProcessingException e) {
            log.error("Redis get() error for key {}: {}", key, e.getMessage());
            return null;
        }
    }

    public void set(String key, Object object , Long time){
        try{
            String jsonValue = mapper.writeValueAsString(object);
            redisTemplate.opsForValue().set(key, jsonValue, time, TimeUnit.SECONDS);
        }catch (Exception e) {
            log.error("Redis set() error for key {}: {}", key, e.getMessage());
            throw new RuntimeException(e);
        }
    }
}