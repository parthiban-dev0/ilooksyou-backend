package com.ilooksyou.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserRequest(
        String fullName,
        @NotBlank(message = "Email is required")
        @Email(message = "Invalid email format")
        String email,
        @NotBlank(message = "Password is required")
        String password,
        String mobileNumber,
        String bio,
        String gender,
        String image) {
    public UserRequest(String email, String password){
        this("",email,password,"","","","");
    }

}
