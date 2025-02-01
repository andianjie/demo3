package org.example.demo3.MQ;

import org.apache.rocketmq.client.apis.*;
import org.apache.rocketmq.client.apis.consumer.ConsumeResult;
import org.apache.rocketmq.client.apis.consumer.FilterExpression;
import org.apache.rocketmq.client.apis.consumer.FilterExpressionType;
import org.apache.rocketmq.client.apis.consumer.PushConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Collections;

public class PushConsumerExample {
    private static final Logger LOGGER = LoggerFactory.getLogger(PushConsumerExample.class);

    private PushConsumerExample() {
    }

    public static void main(String[] args) throws ClientException, IOException, InterruptedException {
        /**
         * 实例接入点，从控制台实例详情页的接入点页签中获取。
         * 如果是在阿里云ECS内网访问，建议填写VPC接入点。
         * 如果是在本地公网访问，或者是线下IDC环境访问，可以使用公网接入点。使用公网接入点访问，必须开启实例的公网访问功能。
         */
        String endpoints = "rmq-cn-83l41s47x04.cn-shanghai.rmq.aliyuncs.com:8080";
        //指定需要订阅哪个目标Topic，Topic需要提前在控制台创建，如果不创建直接使用会返回报错。
        String topic = "topic01";
        //为消费者指定所属的消费者分组，Group需要提前在控制台创建，如果不创建直接使用会返回报错。
        String consumerGroup = "group01";
        String instanceId = "rmq-cn-83l41s47x04";
        String userName = "de2rC7CC7O9zt9LS";
        String passWord= "cEbN1p10nvWdTK72";

        final ClientServiceProvider provider = ClientServiceProvider.loadService();
        ClientConfiguration clientConfiguration = ClientConfiguration.newBuilder()
                .setEndpoints(endpoints)
                .setNamespace(instanceId)
                .setCredentialProvider(new StaticSessionCredentialsProvider(userName, passWord))
                .build();

        //订阅消息的过滤规则，表示订阅所有Tag的消息。
        String tag = "*";
        FilterExpression filterExpression = new FilterExpression(tag, FilterExpressionType.TAG);
        //初始化PushConsumer，需要绑定消费者分组ConsumerGroup、通信参数以及订阅关系。
        PushConsumer pushConsumer = provider.newPushConsumerBuilder()
                .setClientConfiguration(clientConfiguration)
                //设置消费者分组。
                .setConsumerGroup(consumerGroup)
                //设置预绑定的订阅关系。
                .setSubscriptionExpressions(Collections.singletonMap(topic, filterExpression))
                //设置消费监听器。
                .setMessageListener(messageView -> {
                    //处理消息并返回消费结果。
                    LOGGER.info("Consume message={}", messageView.getBody());

                    //System.out.println("Consume Message: " + messageView);
                    return ConsumeResult.SUCCESS;
                })
                .build();
        //Thread.sleep(Long.MAX_VALUE);
        //如果不需要再使用PushConsumer，可关闭该进程。
        //pushConsumer.close();
    }
}