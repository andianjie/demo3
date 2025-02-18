package org.example.demo3.config;

import org.example.demo3.Redis.ExpiredKeyListener;
import org.example.demo3.Redis.RedisMessageListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        
        // 设置连接工厂
        redisTemplate.setConnectionFactory(connectionFactory);
        
        // 设置key的序列化器
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        
        // 设置value的序列化器
        GenericJackson2JsonRedisSerializer jsonRedisSerializer = new GenericJackson2JsonRedisSerializer();
        redisTemplate.setValueSerializer(jsonRedisSerializer);
        redisTemplate.setHashValueSerializer(jsonRedisSerializer);
        
        // 初始化RedisTemplate
        redisTemplate.afterPropertiesSet();
        
        return redisTemplate;
    }

    @Bean
    public ChannelTopic topic() {
        return new ChannelTopic("myRedis");
    }
    @Bean
    public ChannelTopic expiredKeyTopic() {
        return new ChannelTopic("__keyevent@0__:expired");
    }

    @Bean
    public RedisMessageListenerContainer redisMessageListenerContainer(RedisConnectionFactory connectionFactory,
                                                                       RedisMessageListener messageListener,
                                                                       ChannelTopic topic,
                                                                       ExpiredKeyListener expiredKeyListener,
                                                                       ChannelTopic expiredKeyTopic)
    {
        
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener(messageListener, topic);
        container.addMessageListener(expiredKeyListener, expiredKeyTopic);
        return container;
    }
}
