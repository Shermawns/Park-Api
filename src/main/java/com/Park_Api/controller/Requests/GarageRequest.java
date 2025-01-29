package com.Park_Api.controller.Requests;

import com.Park_Api.entity.Client;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import org.hibernate.validator.constraints.br.CPF;

public record GarageRequest(@NotBlank @CPF String clientCpf,
                            @NotBlank @Pattern(regexp = "[A-Z]{3}-[0-9]{4}", message = "The license plate must follow the format XXX-0000")
                            String licensePlate,
                            @NotBlank
                            String model,
                            @NotBlank String carBrand,
                            @NotBlank String color) {
}
