package com.ilooksyou.service;

import com.ilooksyou.message.GeneralMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class SenderServiceImpl implements SenderService<GeneralMessage> {

    @Autowired
    private KafkaTemplate<String, GeneralMessage> kafkaTemplate;

    @Override
    public void sendMessage(String topic, GeneralMessage message) {
        CompletableFuture<SendResult<String, GeneralMessage>> future = kafkaTemplate.send(topic,message);
        future.whenCompleteAsync((result,ex)->{
            log.info("Message sent at {}",result.getRecordMetadata().timestamp());
        });
    }
}
