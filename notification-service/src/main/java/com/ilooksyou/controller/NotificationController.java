package com.ilooksyou.controller;

import com.ilooksyou.message.GeneralMessage;
import com.ilooksyou.message.dto.GeneralMessageRequest;
import com.ilooksyou.service.SenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("api/v1/notification")
public class NotificationController {

    @Autowired
    private SenderService<GeneralMessage> senderService;

    private final static Map<String,String> topicMap = Map.of(
            "email","email-notification",
            "web", "web-notification",
            "android", "android-notification",
            "ios", "ios-notification"
    );

    @PostMapping("send/general")
    public ResponseEntity<?> sendGeneralMessage(@RequestBody GeneralMessageRequest request){
        for(String type: request.types()){
            senderService.sendMessage(topicMap.get(type),request.message());
        }
        return ResponseEntity.ok("Message sent successfully");
    }
}
