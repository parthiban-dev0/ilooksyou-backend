package com.ilooksyou.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "auth-service", url = "${app.client.auth-service}")
public interface AuthServiceClient {

    @GetMapping("api/v1/auth/token-valid")
    boolean tokenValid(@RequestHeader("Authorization") String token);

}
