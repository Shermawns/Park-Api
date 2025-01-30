package com.Park_Api.controller;

import com.Park_Api.controller.Requests.GarageRequest;
import com.Park_Api.controller.Responses.GarageResponse;
import com.Park_Api.entity.Client;
import com.Park_Api.entity.Garage;
import com.Park_Api.entity.User;
import com.Park_Api.exceptions.errors.EntityNotFoundException;
import com.Park_Api.mapper.GarageMapper;
import com.Park_Api.service.ClientHasSpotService;
import com.Park_Api.service.GarageService;
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
public class GarageController {

    private final GarageService garageService;
    private final ClientHasSpotService clientHasSpotService;
    private final GarageMapper garageMapper;

    public GarageController(GarageService garageService, ClientHasSpotService clientHasSpotService, GarageMapper garageMapper) {
        this.garageService = garageService;
        this.clientHasSpotService = clientHasSpotService;
        this.garageMapper = garageMapper;
    }


    @PostMapping(value = "/check-in")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<GarageResponse> create(@Validated @RequestBody GarageRequest garageRequest){

        Garage garage = garageMapper.toGarage(garageRequest);

        garageService.checkIn(garage);

        return ResponseEntity.status(HttpStatus.CREATED).body(garageMapper.toResponse(garage));
    }

    @PostMapping(value = "/check-out/{receipt}")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<GarageResponse> create(@PathVariable String receipt){

        Garage garage = garageService.findByReceipt(receipt);

        garageService.checkOut(receipt);

        return ResponseEntity.status(HttpStatus.CREATED).body(garageMapper.toResponse(garage));
    }

    @GetMapping(value = "/find/receipt/{receipt}")
    @PreAuthorize("hasRole('CLIENT') OR hasRole('ADMIN')")
    public ResponseEntity<GarageResponse> findByReceipt(@PathVariable String receipt){

        Garage garage = garageService.findByReceipt(receipt);

        return ResponseEntity.status(HttpStatus.OK).body(garageMapper.toResponse(garage));
    }

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
