package com.Park_Api.service;

import com.Park_Api.entity.Client;
import com.Park_Api.entity.Garage;
import com.Park_Api.entity.ParkingSpot;
import com.Park_Api.entity.enums.ParkingStatus;
import com.Park_Api.exceptions.errors.EntityNotFoundException;
import com.Park_Api.utils.CalculateValuePerTime;
import com.Park_Api.utils.GarageUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.util.List;


@Service
public class GarageService {

    private final static double DISCOUNT_PRICE = 0.30;
    private final ClienteService clienteService;
    private final ParkingSpotService parkingSpotService;
    private final ClientHasSpotService clientHasSpotService;


    public GarageService(ClienteService clienteService, ParkingSpotService parkingSpotService, ClientHasSpotService clientHasSpotService) {
        this.clienteService = clienteService;
        this.parkingSpotService = parkingSpotService;
        this.clientHasSpotService = clientHasSpotService;
    }

    public Garage checkIn(Garage garage){
        Client client = clienteService.findByCpf(garage.getClient().getCpf());
        garage.setClient(client);

        ParkingSpot parkingSpot = parkingSpotService.findAvaliableSpot();
        parkingSpot.setStatus(ParkingStatus.OCCUPIED);
        garage.setParkingSpot(parkingSpot);

        garage.setEntryDate(LocalDateTime.now());

        garage.setReceipt(GarageUtils.generateReceipt());

        return clientHasSpotService.save(garage);
    }

    public Garage checkOut(String receipt){

        Garage garage = clientHasSpotService.findByReceipt(receipt);

        LocalDateTime exitDate = LocalDateTime.now();

        BigDecimal value = CalculateValuePerTime.CalculateValue(garage.getEntryDate(), exitDate);

        garage.setValue(value);

        garage.getParkingSpot().setStatus(ParkingStatus.AVAILABLE);

        garage.setExitDate(exitDate);

        if (garage.getReceipt().length() == 11){
            BigDecimal discount = new BigDecimal(DISCOUNT_PRICE);
            BigDecimal discountAmount = value.multiply(discount);
            BigDecimal finalValue = value.subtract(discountAmount);

            garage.setValue(finalValue);
        }

        return clientHasSpotService.save(garage);
    }

    public Garage findByReceipt(String receipt) {
        return clientHasSpotService.findByReceipt(receipt);
    }

}
