package com.Park_Api.controller.Responses;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record GarageResponse(
        String licensePlate,
        String carBrand,
        String model,
        String color,
        String clientCpf,
        String receipt,
        LocalDateTime entryDate,
        LocalDateTime exitDate,
        String parkingCod,
        BigDecimal value,
        BigDecimal discount
) {}
