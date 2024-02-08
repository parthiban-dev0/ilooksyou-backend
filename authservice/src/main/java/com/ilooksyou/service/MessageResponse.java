package com.ilooksyou.service;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.stereotype.Service;

@Service
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MessageResponse<T> {

    private boolean success;
    private String message;
    private T data;
    private T error;

    private MessageResponse(){

    }

    public boolean isSuccess(){
        return this.success;
    }

    public String getMessage(){
        return this.message;
    }

    public T getData(){
        return this.data;
    }

    public T getError(){
        return this.error;
    }

    public static class MessageBuilder<T>{

        private boolean success;
        private String message;
        private T data;
        private T error;

        private MessageBuilder(){

        }

        public MessageBuilder<T> setSuccess(boolean success){
            this.success = success;
            return this;
        }

        public MessageBuilder<T> setMessage(String message){
            this.message = message;
            return this;
        }

        public MessageBuilder<T> setData(T data){
            this.data = data;
            return this;
        }

        public MessageBuilder<T> setError(T error){
            this.error = error;
            return this;
        }
        public MessageResponse<T> build(){
            MessageResponse<T> messageResponse = new MessageResponse<>();
            messageResponse.success = success;
            messageResponse.data = data;
            messageResponse.error = error;
            messageResponse.message = message;
            return messageResponse;
        }
    }
    public static <T> MessageBuilder<T> builder(){
        return new MessageBuilder<>();
    }
}
