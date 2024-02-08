package com.ilooksyou.exception;

import com.ilooksyou.service.MessageResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException me){
        record FieldErrors(String field, String error){

        };
        List<FieldErrors> fieldErrors = me.getBindingResult().getFieldErrors().stream().map((fe) -> new FieldErrors(fe.getField(),fe.getDefaultMessage())).toList();
        MessageResponse<List<FieldErrors>> messageResponse = MessageResponse.<List<FieldErrors>>builder().setMessage("Validation error")
                .setError(fieldErrors).build();
        return ResponseEntity.ok(messageResponse);
    }
}
