package com.Park_Api.controller;

import com.Park_Api.controller.Requests.ParkingRequest;
import com.Park_Api.controller.Responses.ParkingResponse;
import com.Park_Api.entity.ParkingSpot;
import com.Park_Api.mapper.ParkingMapper;
import com.Park_Api.service.ParkingSpotService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/V1/parking")
@Tag(name = "Parking API", description = "Endpoints for managing parking spots.")
public class ParkingSpotController {

    private final ParkingSpotService parkingSpotService;
    private final ParkingMapper parkingMapper;

    public ParkingSpotController(ParkingSpotService parkingSpotService, ParkingMapper parkingMapper) {
        this.parkingSpotService = parkingSpotService;
        this.parkingMapper = parkingMapper;
    }

    @Operation(summary = "Create Parking Spot", description = "Creates a new parking spot. " +
            "Only ADMIN users can perform this action.",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Parking spot created successfully",
                            content = @Content(mediaType = "application/json;charset=UTF-8",
                                    schema = @Schema(implementation = ParkingResponse.class))),
            })
    @PostMapping(value = "/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ParkingResponse> create(@Validated @RequestBody ParkingRequest parkingRequest){

        ParkingSpot result = parkingMapper.toParking(parkingRequest);

        ParkingSpot parkingSpot = parkingSpotService.save(result);

        return ResponseEntity.status(HttpStatus.CREATED).body(parkingMapper.toResponse(parkingSpot));
    }


    @Operation(summary = "Find Parking Spot by Code", description = "Retrieves a parking spot by its unique code. " +
            "Only ADMIN users can perform this action.",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Parking spot found",
                            content = @Content(mediaType = "application/json;charset=UTF-8",
                                    schema = @Schema(implementation = ParkingResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Parking spot not found",
                            content = @Content(mediaType = "application/json;charset=UTF-8")),
            })
    @GetMapping(value = "/find/code/{code}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ParkingResponse> findByCode (@PathVariable String code){

        ParkingSpot parkingSpot = parkingSpotService.findParkingSpotByCode(code);

        return ResponseEntity.ok().body(parkingMapper.toResponse(parkingSpot));
    }

}
