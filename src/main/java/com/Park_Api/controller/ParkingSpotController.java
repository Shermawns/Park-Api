package com.Park_Api.controller;

import com.Park_Api.controller.Requests.ParkingRequest;
import com.Park_Api.controller.Responses.ParkingResponse;
import com.Park_Api.entity.ParkingSpot;
import com.Park_Api.mapper.ParkingMapper;
import com.Park_Api.service.ParkingSpotService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/V1/parking")
public class ParkingSpotController {

    private final ParkingSpotService parkingSpotService;
    private final ParkingMapper parkingMapper;

    public ParkingSpotController(ParkingSpotService parkingSpotService, ParkingMapper parkingMapper) {
        this.parkingSpotService = parkingSpotService;
        this.parkingMapper = parkingMapper;
    }

    @PostMapping(value = "/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ParkingResponse> create(@Validated @RequestBody ParkingRequest parkingRequest){

        ParkingSpot result = parkingMapper.toParking(parkingRequest);

        ParkingSpot parkingSpot = parkingSpotService.save(result);

        return ResponseEntity.status(HttpStatus.CREATED).body(parkingMapper.toResponse(parkingSpot));
    }

    @GetMapping(value = "/find/code/{code}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ParkingResponse> findByCode (@PathVariable String code){

        ParkingSpot parkingSpot = parkingSpotService.findParkingSpotByCode(code);

        return ResponseEntity.ok().body(parkingMapper.toResponse(parkingSpot));
    }
}
