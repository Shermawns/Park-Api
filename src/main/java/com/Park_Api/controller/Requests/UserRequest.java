package com.Park_Api.controller.Requests;

import com.Park_Api.entity.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRequest (

        @NotBlank
        @Email(message = "Email must be a valid format")
        String username,

        @NotBlank(message = "Password cannot be blank")
        @Size(min = 6)
        String password,
        Role role,
        String createdBy
){}
