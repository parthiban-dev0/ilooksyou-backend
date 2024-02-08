package com.ilooksyou.controller;

import com.ilooksyou.dto.UserRequest;
import com.ilooksyou.dto.UserResponse;
import com.ilooksyou.service.MessageResponse;
import com.ilooksyou.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("create")
    public ResponseEntity<?> createUser(@Valid @RequestBody UserRequest userRequest){
        UserResponse userResponse = userService.createUser(userRequest);
        MessageResponse<UserResponse> messageResponse = MessageResponse.<UserResponse>builder()
                .setMessage("User created successfully").setData(userResponse)
                .setSuccess(true).build();
        return ResponseEntity.ok(messageResponse);
    }

    @GetMapping("list")
    public ResponseEntity<?> getUser(@RequestParam Map<String, String> params){
        if(params.isEmpty()){
            List<UserResponse> userResponses = userService.listUsers();
            MessageResponse<List<UserResponse>> messageResponse = MessageResponse.<List<UserResponse>>builder()
                    .setMessage("User list fetched successfully").setData(userResponses)
                    .setSuccess(true).build();
            return ResponseEntity.ok(messageResponse);
        }else if(params.containsKey("email")){
            Optional<UserResponse> userResponse = userService.getUserByEmail(params.get("email"));
            MessageResponse<UserResponse> messageResponse;
            if(userResponse.isEmpty()){
                messageResponse = MessageResponse.<UserResponse>builder()
                        .setMessage("User not found").setSuccess(false).build();
            }else{
                messageResponse = MessageResponse.<UserResponse>builder()
                        .setMessage("User fetched successfully").setData(userResponse.get())
                        .setSuccess(true).build();
            }
            return ResponseEntity.ok(messageResponse);
        }
        return ResponseEntity.ok("no content");
    }
}
