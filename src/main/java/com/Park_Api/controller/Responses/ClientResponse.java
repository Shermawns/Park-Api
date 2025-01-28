package com.Park_Api.controller.Responses;

import com.Park_Api.entity.enums.Role;

import java.time.LocalDate;

public record ClientResponse (Long id,
                              String name,
                              String cpf,
                              Role role,
                              LocalDate createdDate,
                              LocalDate modificationDate,
                              String createdBy,
                              String updateBy){
}
