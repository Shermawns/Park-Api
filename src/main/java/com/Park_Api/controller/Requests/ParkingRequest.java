package com.Park_Api.controller.Requests;

import com.Park_Api.entity.enums.ParkingStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ParkingRequest(
        @NotBlank
        @Size(min = 4, max = 4, message = "Code must be exactly 4 characters long.")
        String code,

        @NotNull(message = "Parking status cannot be null.")
        ParkingStatus parkingStatus
) {
}
