package org.example.demo3.Redis;

import org.example.demo3.mapper.CouponMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;


@Component
public class ExpiredKeyListener implements MessageListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExpiredKeyListener.class);

    @Autowired
    private CouponMapper couponMapper;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String channel = new String(pattern);
        String messageBody = new String(message.getBody());

        LOGGER.info("收到Redis消息通知：");
        LOGGER.info("频道：{}", channel);
        LOGGER.info("消息内容：{}", messageBody);
        // 这里可以添加您的业务逻辑
        // 比如解析消息内容，进行相应处理
        String id = messageBody.split(":")[1];
        LOGGER.info("过期的key为：{}", id);
        couponMapper.updateState(Long.parseLong(id),1);

    }
}
