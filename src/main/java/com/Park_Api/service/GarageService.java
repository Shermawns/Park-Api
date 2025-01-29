package com.Park_Api.service;

import com.Park_Api.entity.Client;
import com.Park_Api.entity.Garage;
import com.Park_Api.entity.ParkingSpot;
import com.Park_Api.entity.enums.ParkingStatus;
import com.Park_Api.utils.GarageUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class GarageService {

    private final ClienteService clienteService;
    private final ParkingSpotService parkingSpotService;
    private final ClientHasSpotService clientHasSpot;


    public GarageService(ClienteService clienteService, ParkingSpotService parkingSpotService, ClientHasSpotService clientHasSpotService) {
        this.clienteService = clienteService;
        this.parkingSpotService = parkingSpotService;
        this.clientHasSpot = clientHasSpotService;
    }

    public Garage checkIn(Garage garage){
        Client client = clienteService.findByCpf(garage.getClient().getCpf());
        garage.setClient(client);

        ParkingSpot parkingSpot = parkingSpotService.findAvaliableSpot();
        parkingSpot.setStatus(ParkingStatus.OCCUPIED);
        garage.setParkingSpot(parkingSpot);

        garage.setEntryDate(LocalDateTime.now());

        garage.setReceipt(GarageUtils.generateReceipt());

        return clientHasSpot.save(garage);
    }
}
