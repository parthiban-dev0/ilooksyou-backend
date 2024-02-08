package com.ilooksyou.client;

import com.ilooksyou.dto.ManualLoginRequest;
import com.ilooksyou.dto.UserResponse;
import com.ilooksyou.service.MessageResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "user-service", url = "${app.client.user-service}")
public interface UserServiceClient {

    @GetMapping("api/v1/users/list")
    public MessageResponse<UserResponse> getUserByEmail(@RequestParam("email") String email);

    @PostMapping("api/v1/users/create")
    public MessageResponse<?> createUser(@RequestBody ManualLoginRequest manualLoginRequest);
}
