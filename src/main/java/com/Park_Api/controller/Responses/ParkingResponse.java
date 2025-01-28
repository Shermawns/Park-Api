package com.Park_Api.controller.Responses;

import com.Park_Api.entity.enums.ParkingStatus;

public record ParkingResponse(Long id,
                              String code,
                              ParkingStatus status) {
}
