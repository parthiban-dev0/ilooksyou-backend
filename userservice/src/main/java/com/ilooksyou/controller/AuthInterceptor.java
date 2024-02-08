package com.ilooksyou.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ilooksyou.client.AuthServiceClient;
import com.ilooksyou.service.MessageResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class AuthInterceptor implements HandlerInterceptor {

    @Autowired
    private AuthServiceClient authServiceClient;

    private final Set<String> omitPaths;

    public AuthInterceptor(){
        omitPaths = new HashSet<>(
                Arrays.asList("GET /api/v1/users/list","POST /api/v1/users/create"));
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
        throws Exception
    {
        String path = request.getMethod() + " " +request.getRequestURI();
        if(omitPaths.contains(path)){
            return true;
        }
        String token = request.getHeader("Authorization");
        if(token == null){
            MessageResponse<?> messageResponse = MessageResponse.builder().setMessage("Authorization token is missing in header").build();
            writeResponse(response,messageResponse);
            return false;
        }
        if(!authServiceClient.tokenValid(token)){
            MessageResponse<?> messageResponse = MessageResponse.builder().setMessage("Authentication failed").build();
            writeResponse(response,messageResponse);
            return false;
        }
        return true;
    }

    private void writeResponse(HttpServletResponse response, MessageResponse<?> messageResponse)
        throws Exception
    {
        ObjectMapper mapper = new ObjectMapper();
        response.setHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(mapper.writeValueAsString(messageResponse));
    }

}
