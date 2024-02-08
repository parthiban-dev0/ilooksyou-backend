package com.ilooksyou.dto;

public record SigninResponse(String email, String accessToken, String refreshToken) {
}
