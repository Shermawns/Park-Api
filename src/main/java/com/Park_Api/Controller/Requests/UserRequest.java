package com.Park_Api.Controller.Requests;

import com.Park_Api.entity.enums.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record UserRequest (

        @NotBlank
        @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",
                message = "Email must be a valid format")
        String username,

        @NotBlank(message = "Password cannot be blank")
        String password,
        Role role,
        String createdBy
){}
