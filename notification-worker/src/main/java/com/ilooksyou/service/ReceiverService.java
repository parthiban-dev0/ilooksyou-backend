package com.ilooksyou.service;

import com.ilooksyou.message.GeneralMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ReceiverService {

    @KafkaListener(groupId = "${kafka.groupId}",topics = "email-notification", containerFactory = "listenerContainerFactory")
    public void handleEmailGeneralMessage(GeneralMessage message){
        log.info("Message received : {}",message.getContent());
    }
}
