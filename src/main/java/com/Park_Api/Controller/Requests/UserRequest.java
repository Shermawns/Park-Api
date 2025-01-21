package com.Park_Api.Controller.Requests;

import com.Park_Api.entity.enums.Role;

public record UserRequest (
        String username,
        String password,
        Role role,
        String createdBy
){}
