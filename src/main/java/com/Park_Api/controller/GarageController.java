package com.Park_Api.controller;

import com.Park_Api.controller.Requests.GarageRequest;
import com.Park_Api.controller.Responses.GarageResponse;
import com.Park_Api.entity.Garage;
import com.Park_Api.entity.User;
import com.Park_Api.exceptions.errors.EntityNotFoundException;
import com.Park_Api.mapper.GarageMapper;
import com.Park_Api.service.ClientHasSpotService;
import com.Park_Api.service.GarageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "api/V1/garage")
@Tag(name = "Garage API", description = "Endpoints for managing garage operations, check-in, and check-out.")
public class GarageController {

    private final GarageService garageService;
    private final ClientHasSpotService clientHasSpotService;
    private final GarageMapper garageMapper;

    public GarageController(GarageService garageService, ClientHasSpotService clientHasSpotService, GarageMapper garageMapper) {
        this.garageService = garageService;
        this.clientHasSpotService = clientHasSpotService;
        this.garageMapper = garageMapper;
    }

    @Operation(summary = "Check-in a Vehicle", description = "Registers a vehicle's check-in in the garage. " +
            "Only ADMIN users can perform this action.",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Check-in successful",
                            content = @Content(mediaType = "application/json;charset=UTF-8",
                                    schema = @Schema(implementation = GarageResponse.class))),
            })
    @PostMapping(value = "/check-in")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<GarageResponse> create(@Validated @RequestBody GarageRequest garageRequest){

        Garage garage = garageMapper.toGarage(garageRequest);

        garageService.checkIn(garage);

        return ResponseEntity.status(HttpStatus.CREATED).body(garageMapper.toResponse(garage));
    }


    @Operation(summary = "Check-out a Vehicle", description = "Registers a vehicle's check-out using the receipt number. " +
            "Only ADMIN users can perform this action.",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Check-out successful",
                            content = @Content(mediaType = "application/json;charset=UTF-8",
                                    schema = @Schema(implementation = GarageResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Receipt not found",
                            content = @Content(mediaType = "application/json;charset=UTF-8")),
            })
    @PostMapping(value = "/check-out/{receipt}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<GarageResponse> create(@PathVariable String receipt){

        Garage garage = garageService.findByReceipt(receipt);

        garageService.checkOut(receipt);

        return ResponseEntity.status(HttpStatus.CREATED).body(garageMapper.toResponse(garage));
    }

    @Operation(summary = "Find Garage by Receipt", description = "Finds a garage entry by receipt number. " +
            "Accessible to both CLIENT and ADMIN users.",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Garage entry found",
                            content = @Content(mediaType = "application/json;charset=UTF-8",
                                    schema = @Schema(implementation = GarageResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Garage entry not found",
                            content = @Content(mediaType = "application/json;charset=UTF-8")),
            })

    @GetMapping(value = "/find/receipt/{receipt}")
    @PreAuthorize("hasRole('CLIENT') OR hasRole('ADMIN')")
    public ResponseEntity<GarageResponse> findByReceipt(@PathVariable String receipt){

        Garage garage = garageService.findByReceipt(receipt);

        return ResponseEntity.status(HttpStatus.OK).body(garageMapper.toResponse(garage));
    }

    @Operation(summary = "Find Garage Entries by CPF",
            description = "Retrieves all garage entries associated with a client's CPF. " +
                    "Only accessible to ADMIN users.",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of garage entries retrieved",
                            content = @Content(mediaType = "application/json;charset=UTF-8",
                                    array = @ArraySchema(schema = @Schema(implementation = GarageResponse.class)))),
                    @ApiResponse(responseCode = "404", description = "No garage entries found for the given CPF",
                            content = @Content(mediaType = "application/json;charset=UTF-8")),
                    @ApiResponse(responseCode = "403", description = "Access denied",
                            content = @Content(mediaType = "application/json;charset=UTF-8"))
            })
    @GetMapping(value = "/find/cpf/{cpf}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<GarageResponse>> findByCpf(@PathVariable String cpf){
        List<Garage> list = clientHasSpotService.findByClientCpf(cpf);
        if (list.isEmpty()) {
            throw new EntityNotFoundException("This client doesn't have an garage");
        }
        return ResponseEntity.ok(
                list.stream()
                        .map(garageMapper::toResponse)
                        .collect(Collectors.toList())
        );
    }


    @Operation(summary = "Find My Garage Entries",
            description = "Retrieves all garage entries associated with the authenticated client’s CPF. " +
                    "Only CLIENT users can access their own records.",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of garage entries retrieved",
                            content = @Content(mediaType = "application/json;charset=UTF-8",
                                    array = @ArraySchema(schema = @Schema(implementation = GarageResponse.class)))),
                    @ApiResponse(responseCode = "403", description = "The provided CPF does not match the authenticated client",
                            content = @Content(mediaType = "application/json;charset=UTF-8"))
            })
    @GetMapping(value = "/find/my-garage/{cpf}")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<List<GarageResponse>> findByMyCpf(@AuthenticationPrincipal User user, @PathVariable String cpf) {

        if (!user.getClient().getCpf().equals(cpf)) {
            throw new AccessDeniedException("The CPF does not match the authenticated client.");
        }

        List<Garage> list = clientHasSpotService.findByClientCpf(cpf);

        return ResponseEntity.ok(
                list.stream().map(garageMapper::toResponse)
                        .collect(Collectors.toList())
        );
    }


}
