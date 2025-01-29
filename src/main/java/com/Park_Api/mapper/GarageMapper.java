package com.Park_Api.mapper;

import com.Park_Api.controller.Requests.GarageRequest;
import com.Park_Api.controller.Responses.GarageResponse;
import com.Park_Api.entity.Garage;
import com.Park_Api.service.ClientHasSpotService;
import org.springframework.stereotype.Component;

@Component
public class GarageMapper {

    private final ClientHasSpotService clientHasSpotService;

    public GarageMapper(ClientHasSpotService clientHasSpotService) {
        this.clientHasSpotService = clientHasSpotService;
    }

    public Garage toGarage(GarageRequest garageRequest){
        return Garage.builder()
                .client(clientHasSpotService.findByCpf(garageRequest.clientCpf()))
                .model(garageRequest.model())
                .licensePlate(garageRequest.licensePlate())
                .carBrand(garageRequest.carBrand())
                .color(garageRequest.color())
                .build();
    }

    public GarageResponse toResponse(Garage garage){
        return new GarageResponse(
                garage.getLicensePlate(),
                garage.getCarBrand(),
                garage.getModel(),
                garage.getColor(),
                garage.getClient().getCpf(),
                garage.getReceipt(),
                garage.getEntryDate(),
                garage.getExitDate(),
                garage.getParkingSpot().getCod(),
                garage.getValue(),
                garage.getDiscount()
        );
    }
}
