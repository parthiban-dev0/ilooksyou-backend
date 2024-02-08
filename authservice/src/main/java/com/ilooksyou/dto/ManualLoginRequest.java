package com.ilooksyou.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class ManualLoginRequest {
    @NotNull(message = "Email is required")
    @Email(message = "Email is invalid")
    private String email;
    @NotNull(message = "Password is required")
    private String password;
}
