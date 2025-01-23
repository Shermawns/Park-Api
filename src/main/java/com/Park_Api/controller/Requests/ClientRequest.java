package com.Park_Api.controller.Requests;

import org.hibernate.validator.constraints.br.CPF;

public record ClientRequest (String name,
                             @CPF
                             String cpf){
}
