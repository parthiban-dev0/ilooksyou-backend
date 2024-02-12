package com.ilooksyou.controller;

import com.ilooksyou.model.dto.TemplateRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/email")
public class EmailController {

    @PostMapping("template/upload")
    public ResponseEntity<?> uploadTemplate(@RequestBody TemplateRequest request){
        return null;
    }
}
