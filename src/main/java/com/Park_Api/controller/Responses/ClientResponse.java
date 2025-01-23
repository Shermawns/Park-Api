package com.Park_Api.controller.Responses;

import com.Park_Api.entity.enums.Role;

public record ClientResponse (Long id,
                              String name,
                              String cpf,
                              Role role){
}
