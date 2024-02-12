package com.ilooksyou.controller;

import com.ilooksyou.model.Template;
import com.ilooksyou.model.dto.EmailSendRequest;
import com.ilooksyou.model.dto.TemplateRequest;
import com.ilooksyou.service.EmailSender;
import com.ilooksyou.service.MessageResponse;
import com.ilooksyou.service.TemplateService;
import feign.Response;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/email")
@CrossOrigin("*")
public class EmailController {

    @Autowired
    private TemplateService templateService;

    @Autowired
    private EmailSender emailSender;

    @PostMapping("template/create")
    public ResponseEntity<?> createTemplate(@Valid @RequestBody TemplateRequest request){
        Template template = templateService.create(request);
        MessageResponse<?> response = MessageResponse.builder()
                .setMessage("Template created successfully")
                .setData(template).setSuccess(true).build();
        return ResponseEntity.ok(response);
    }

    @PutMapping("template/update/{templateId}")
    public ResponseEntity<?> updateTemplate(){
        return null;
    }

    @DeleteMapping("template/delete/{templateId}")
    public ResponseEntity<?> deleteTemplate(){
        return null;
    }

    @PostMapping("send")
    public ResponseEntity<?> sendMail(@Valid @RequestBody EmailSendRequest request){
        emailSender.sendMail(request);
        MessageResponse<?> response = MessageResponse.builder()
                .setMessage("Email sent successfully")
                .setSuccess(true).build();
        return ResponseEntity.ok(response);
    }
}
