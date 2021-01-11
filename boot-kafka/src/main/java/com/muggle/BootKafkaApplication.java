package com.muggle;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableKafka@EnableScheduling
@EnableKafka
public class BootKafkaApplication {

    public static void main(String[] args) {
        SpringApplication.run(BootKafkaApplication.class, args);
    }

}
