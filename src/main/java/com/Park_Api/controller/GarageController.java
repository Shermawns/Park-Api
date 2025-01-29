package com.Park_Api.controller;

import com.Park_Api.controller.Requests.GarageRequest;
import com.Park_Api.controller.Requests.ParkingRequest;
import com.Park_Api.controller.Responses.GarageResponse;
import com.Park_Api.controller.Responses.ParkingResponse;
import com.Park_Api.entity.Garage;
import com.Park_Api.entity.ParkingSpot;
import com.Park_Api.mapper.GarageMapper;
import com.Park_Api.service.GarageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "api/V1/garage")
public class GarageController {

    private final GarageService garageService;
    private final GarageMapper garageMapper;

    public GarageController(GarageService garageService, GarageMapper garageMapper) {
        this.garageService = garageService;
        this.garageMapper = garageMapper;
    }


    @PostMapping(value = "/create")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<GarageResponse> create(@Validated @RequestBody GarageRequest garageRequest){

        Garage garage = garageMapper.toGarage(garageRequest);

        garageService.checkIn(garage);

        return ResponseEntity.status(HttpStatus.CREATED).body(garageMapper.toResponse(garage));
    }
}
