package com.Park_Api.controller.Requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record PasswordRequest(
        @NotBlank
        @Size(min = 6)
        String currentPassword,
        @Size(min = 6)
        String newPassword,
        @Size(min = 6)
        String confirmNewPassword
){}
