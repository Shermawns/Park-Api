package com.Park_Api.Controller.Responses;

import com.Park_Api.entity.enums.Role;

import java.time.LocalDate;

public record UserResponse(Long id,
                           String username,
                           Role role,
                           LocalDate createdDate,
                           LocalDate modificationDate,
                           String createdBy,
                           String updateBy) {
}
