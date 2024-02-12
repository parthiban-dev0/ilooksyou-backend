package com.ilooksyou.config;

import com.ilooksyou.message.GeneralMessage;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class AppConfig {

    @Value("${kafka.concurrency:1}")
    private Integer concurrency;

    @Value("${kafka.brokerUrls}")
    private String brokerUrls;

    @Value("${kafka.groupId}")
    private String groupId;

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, GeneralMessage> listenerContainerFactory(){
        ConcurrentKafkaListenerContainerFactory<String, GeneralMessage> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConcurrency(concurrency);
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }

    @Bean
    public ConsumerFactory<String, GeneralMessage> consumerFactory(){
        DefaultKafkaConsumerFactory<String, GeneralMessage> factory = new DefaultKafkaConsumerFactory<>(configs(),new StringDeserializer(),new JsonDeserializer<>(GeneralMessage.class));
        return factory;
    }

    private Map<String,Object> configs(){
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG,brokerUrls);
        configs.put(AdminClientConfig.CLIENT_ID_CONFIG,groupId);
        return configs;
    }
}
