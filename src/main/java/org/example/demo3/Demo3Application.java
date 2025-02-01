package org.example.demo3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootApplication
@ComponentScan({"org.example.demo3", "org.example.demo3.MQ"})
public class Demo3Application {
    private static final Logger LOGGER = LoggerFactory.getLogger(Demo3Application.class);

    public static void main(String[] args) {
        LOGGER.info("=== Application Starting ===");
        LOGGER.info("Current package: {}", Demo3Application.class.getPackage().getName());
        SpringApplication.run(Demo3Application.class, args);
    }
}
