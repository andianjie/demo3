package org.example.demo3.Redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/redis")
public class testRedis {
    private static final Logger LOGGER = LoggerFactory.getLogger(testRedis.class);

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @GetMapping("/test")
    public String testRedis() {
        try {
            // 测试字符串操作
            String key = "test:string";
            redisTemplate.opsForValue().set(key, "Hello Redis!", 1, TimeUnit.HOURS);
            String value = (String) redisTemplate.opsForValue().get(key);
            LOGGER.info("String value from Redis: {}", value);

            // 测试对象操作
            TestUser user = new TestUser("张三", 25);
            String userKey = "test:user";
            redisTemplate.opsForValue().set(userKey, user);
            TestUser savedUser = (TestUser) redisTemplate.opsForValue().get(userKey);
            LOGGER.info("User from Redis: {}", savedUser);

            // 测试键是否存在
            Boolean hasKey = redisTemplate.hasKey(key);
            LOGGER.info("Key {} exists: {}", key, hasKey);

            return "Redis测试成功！\n" +
                   "String值: " + value + "\n" +
                   "用户对象: " + savedUser;

        } catch (Exception e) {
            LOGGER.error("Redis测试失败", e);
            return "Redis测试失败: " + e.getMessage();
        }
    }
}

// 测试用的用户类
class TestUser {
    private String name;
    private int age;

    // 必须有无参构造函数，因为JSON序列化需要
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
