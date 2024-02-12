package com.ilooksyou.config;

import com.ilooksyou.message.GeneralMessage;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class AppConfig {

    @Value("${kafka.brokerUrls}")
    private String brokerUrls;

    @Bean
    public NewTopic emailTopic(){
        return TopicBuilder.name("email-notification")
                .partitions(3).replicas(1)
                .build();
    }

    @Bean
    public NewTopic webTopic(){
        return TopicBuilder.name("web-notification")
                .partitions(3).replicas(1)
                .build();
    }

    @Bean
    public NewTopic smsTopic(){
        return TopicBuilder.name("sms-notification")
                .partitions(3).replicas(1)
                .build();
    }

    @Bean
    public NewTopic androidTopic(){
        return TopicBuilder.name("android-notification")
                .partitions(3).replicas(1)
                .build();
    }

    @Bean
    public NewTopic iosTopic(){
        return TopicBuilder.name("ios-notification")
                .partitions(3).replicas(1)
                .build();
    }

    @Bean
    public ProducerFactory<String, GeneralMessage> producerFactory(){
        DefaultKafkaProducerFactory<String, GeneralMessage> factory = new DefaultKafkaProducerFactory<>(producerConfigs());
        return factory;
    }

    private Map<String,Object> producerConfigs(){
        Map<String,Object> config = new HashMap<>();
        config.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG,brokerUrls);
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return config;
    }

    @Bean
    public KafkaTemplate<String, GeneralMessage> kafkaTemplate(){
        KafkaTemplate<String, GeneralMessage> kafkaTemplate = new KafkaTemplate<>(producerFactory());
        return kafkaTemplate;
    }
}
