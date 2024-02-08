package com.ilooksyou.controller;

import com.ilooksyou.client.UserServiceClient;
import com.ilooksyou.dto.*;
import com.ilooksyou.model.Token;
import com.ilooksyou.repository.TokenRepository;
import com.ilooksyou.service.GoogleSignInService;
import com.ilooksyou.service.MessageResponse;
import com.ilooksyou.util.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/auth")
public class AuthController {

    @Autowired
    private UserServiceClient userServiceClient;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private GoogleSignInService googleSignInService;

    @GetMapping("access-token")
    public ResponseEntity<?> accessToken(@RequestHeader("Authorization") String refreshToken) {
        UserDto userDto = JwtUtil.parseToken(refreshToken);
        Optional<Token> optionalToken = tokenRepository.findByRefreshToken(refreshToken);
        if (optionalToken.isEmpty()) {
            MessageResponse<?> response = MessageResponse.builder()
                    .setMessage("Refresh Token doesn't exists")
                    .setSuccess(false).build();
            return ResponseEntity.ok(response);
        }
        Token token = optionalToken.get();
        String accessToken = JwtUtil.createToken(userDto);
        token.setAccessToken(accessToken);
        token.setUpdatedAt(LocalDateTime.now());
        tokenRepository.save(token);
        SigninResponse signinResponse = new SigninResponse(userDto.email(), token.getAccessToken(), token.getRefreshToken());
        MessageResponse<?> response = MessageResponse.builder()
                .setMessage("Access token granted")
                .setData(signinResponse).setSuccess(true).build();
        return ResponseEntity.ok(response);
    }

    @PostMapping("sign-in/manual")
    public ResponseEntity<?> manualSignIn(@RequestBody ManualLoginRequest manualLoginRequest) {
        MessageResponse<UserResponse> messageResponse = userServiceClient.getUserByEmail(manualLoginRequest.getEmail());
        if (messageResponse.isSuccess()) {
            UserResponse userResponse = messageResponse.getData();
            if (!passwordEncoder.matches(manualLoginRequest.getPassword(), userResponse.password())) {
                MessageResponse<?> response = MessageResponse.builder()
                        .setMessage("Password is incorrect").setSuccess(false).build();
                return ResponseEntity.ok(response);
            }
            UserDto userDto = new UserDto(userResponse.email(), userResponse.fullName());
            String accessToken = JwtUtil.createToken(userDto);
            String refreshToken = JwtUtil.createToken(userDto);
            Token token = new Token();
            token.setAccessToken(accessToken);
            token.setRefreshToken(refreshToken);
            token.setCreatedAt(LocalDateTime.now());
            token.setUpdatedAt(LocalDateTime.now());
            token = tokenRepository.save(token);
            MessageResponse<SigninResponse> response = MessageResponse.<SigninResponse>builder()
                    .setData(new SigninResponse(userDto.email(), token.getAccessToken(), token.getRefreshToken()))
                    .setMessage("User logged in successfully")
                    .setSuccess(true).build();
            return ResponseEntity.ok(response);
        }
        MessageResponse<?> response = MessageResponse.builder()
                .setMessage("User doesn't exists")
                .setSuccess(false).build();
        return ResponseEntity.ok(response);
    }

    @PostMapping("sign-in/google")
    public ResponseEntity<?> googleSignIn(@RequestParam("google-token") String googleToken) {
        Optional<GoogleSignin> optionalGoogleSignin = googleSignInService.validateToken(googleToken);
        if(optionalGoogleSignin.isEmpty()){
            MessageResponse<?> response = MessageResponse.builder()
                    .setMessage("Invalid ID Token").setSuccess(false).build();
            return ResponseEntity.ok(response);
        }
        GoogleSignin googleSignin = optionalGoogleSignin.get();
        MessageResponse<UserResponse> messageResponse = userServiceClient.getUserByEmail(googleSignin.email());
        if (messageResponse.isSuccess()) {
            UserResponse userResponse = messageResponse.getData();
            UserDto userDto = new UserDto(userResponse.email(), userResponse.fullName());
            String accessToken = JwtUtil.createToken(userDto);
            String refreshToken = JwtUtil.createToken(userDto);
            Token token = new Token();
            token.setAccessToken(accessToken);
            token.setRefreshToken(refreshToken);
            token.setCreatedAt(LocalDateTime.now());
            token.setUpdatedAt(LocalDateTime.now());
            token = tokenRepository.save(token);
            MessageResponse<SigninResponse> response = MessageResponse.<SigninResponse>builder()
                    .setData(new SigninResponse(userDto.email(), token.getAccessToken(), token.getRefreshToken()))
                    .setMessage("User logged in successfully").setSuccess(true).build();
            return ResponseEntity.ok(response);
        }
        MessageResponse<?> response = MessageResponse.builder()
                .setMessage("User doesn't exists").setSuccess(false).build();
        return ResponseEntity.ok(response);
    }

    @PostMapping("signin/facebook")
    public ResponseEntity<?> facebookSignIn() {

        return null;
    }

    @PostMapping("sign-up/manual")
    public ResponseEntity<?> manualSignUp(@Valid @RequestBody ManualLoginRequest manualLoginRequest) {
        manualLoginRequest.setPassword(passwordEncoder.encode(manualLoginRequest.getPassword()));
        MessageResponse<?> messageResponse = userServiceClient.createUser(manualLoginRequest);
        if (messageResponse.isSuccess()) {
            MessageResponse<?> response = MessageResponse.builder()
                    .setMessage("User account created successfully")
                    .setSuccess(true).build();
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.ok(messageResponse);
    }

    @GetMapping("token-valid")
    public ResponseEntity<?> tokenValid() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok(authentication.isAuthenticated());
    }

}
