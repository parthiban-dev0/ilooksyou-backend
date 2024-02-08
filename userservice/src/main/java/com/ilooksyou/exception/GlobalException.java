package com.ilooksyou.exception;

import com.ilooksyou.service.MessageResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.List;

@ControllerAdvice
public class GlobalException {

    @ExceptionHandler(UserException.class)
    public ResponseEntity<ErrorDetails> UserExceptionHandler(UserException ue, WebRequest req){

        ErrorDetails err= new ErrorDetails(ue.getMessage(),req.getDescription(false),LocalDateTime.now());

        return new ResponseEntity<ErrorDetails>(err,HttpStatus.BAD_REQUEST);

    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException me){
        record FieldErrors(String field, String error){

        };
        List<FieldErrors> fieldErrors = me.getBindingResult().getFieldErrors().stream().map((fe) -> new FieldErrors(fe.getField(),fe.getDefaultMessage())).toList();
        MessageResponse<List<FieldErrors>> messageResponse = MessageResponse.<List<FieldErrors>>builder().setMessage("Validation error")
                .setError(fieldErrors).build();
        return ResponseEntity.ok(messageResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> OtherExceptionHandler(Exception ue, WebRequest req){

        ErrorDetails err= new ErrorDetails(ue.getMessage(),req.getDescription(false),LocalDateTime.now());

        return new ResponseEntity<ErrorDetails>(err,HttpStatus.BAD_REQUEST);

    }
}
