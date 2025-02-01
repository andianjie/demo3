package org.example.demo3.Redis;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class RedisTemplateTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(RedisTemplateTest.class);

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Test
    public void testString() {
        // 测试字符串操作
        String key = "test:string";
        String value = "Hello Redis!";
        
        // 设置值
        redisTemplate.opsForValue().set(key, value, 1, TimeUnit.HOURS);
        
        // 获取值并验证
        String retrievedValue = (String) redisTemplate.opsForValue().get(key);
        assertEquals(value, retrievedValue);
        LOGGER.info("String test passed. Value: {}", retrievedValue);
    }

    @Test
    public void testObject() {
        // 测试对象操作
        String key = "test:user";
        TestUser user = new TestUser("张三", 25);
        
        // 存储对象
        redisTemplate.opsForValue().set(key, user);
        
        // 获取对象并验证
        TestUser savedUser = (TestUser) redisTemplate.opsForValue().get(key);
        assertNotNull(savedUser);
        assertEquals(user.getName(), savedUser.getName());
        assertEquals(user.getAge(), savedUser.getAge());
        LOGGER.info("Object test passed. User: {}", savedUser);
    }

    @Test
    public void testKeyOperations() {
        String key = "test:exists";
        
        // 设置值
        redisTemplate.opsForValue().set(key, "test");
        
        // 测试键是否存在
        assertTrue(redisTemplate.hasKey(key));
        
        // 删除键
        redisTemplate.delete(key);
        
        // 验证键已被删除
        assertFalse(redisTemplate.hasKey(key));
        LOGGER.info("Key operations test passed");
    }

    @Test
    public void testExpire() throws InterruptedException {
        String key = "test:expire";
        
        // 设置值，1秒后过期
        redisTemplate.opsForValue().set(key, "will expire", 1, TimeUnit.SECONDS);
        
        // 立即检查，键应该存在
        assertTrue(redisTemplate.hasKey(key));
        
        // 等待2秒
        Thread.sleep(2000);
        
        // 键应该已经过期
        assertFalse(redisTemplate.hasKey(key));
        LOGGER.info("Expiration test passed");
    }
}

// 如果TestUser类不在同一个包中，需要在这里定义
class TestUser {
    private String name;
    private int age;

    public TestUser() {
    }

    public TestUser(String name, int age) {
        this.name = name;
        this.age = age;
    }

    // Getter和Setter
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "TestUser{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
} 