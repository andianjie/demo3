package org.example.demo3.Redis;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
public class RedisMessageTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(RedisMessageTest.class);

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Test
    public void testPublishMessage() {
        String channel = "messageQueue";
        String message = "Hello, This is a test message! " + System.currentTimeMillis();
        
        LOGGER.info("发送消息到频道：{}", channel);
        LOGGER.info("消息内容：{}", message);
        
        redisTemplate.convertAndSend(channel, message);
        
        // 等待一会儿，确保消息被处理
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
} 