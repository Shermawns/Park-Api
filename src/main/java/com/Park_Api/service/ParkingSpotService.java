package com.Park_Api.service;

import com.Park_Api.entity.Client;
import com.Park_Api.entity.ParkingSpot;
import com.Park_Api.exceptions.errors.EntityNotFoundException;
import com.Park_Api.repository.ParkingSpotRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParkingSpotService {

    private final ParkingSpotRepository parkingSpotRepository;

    public ParkingSpotService(ParkingSpotRepository parkingSpotRepository) {
        this.parkingSpotRepository = parkingSpotRepository;
    }

    public ParkingSpot save(ParkingSpot ParkingSpot){

        return parkingSpotRepository.save(ParkingSpot);
    }

    public ParkingSpot findById(Long id){

        ParkingSpot userId = parkingSpotRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("Client %s not found in the system", id)));

        return userId;
    }

    public List<ParkingSpot> findAll(){
        return parkingSpotRepository.findAll();
    }


    public ParkingSpot findParkingSpotByCode(String code) {
        return parkingSpotRepository.findParkingSpotByCod(code);
    }
}
