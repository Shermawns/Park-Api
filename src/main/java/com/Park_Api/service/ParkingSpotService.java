package com.Park_Api.service;

import com.Park_Api.entity.Client;
import com.Park_Api.entity.Garage;
import com.Park_Api.entity.ParkingSpot;
import com.Park_Api.entity.enums.ParkingStatus;
import com.Park_Api.exceptions.errors.EntityNotFoundException;
import com.Park_Api.repository.ParkingSpotRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.Park_Api.entity.enums.ParkingStatus.AVAILABLE;

@Service
public class ParkingSpotService {

    private final ParkingSpotRepository parkingSpotRepository;

    public ParkingSpotService(ParkingSpotRepository parkingSpotRepository) {
        this.parkingSpotRepository = parkingSpotRepository;
    }

    public ParkingSpot save(ParkingSpot ParkingSpot){

        return parkingSpotRepository.save(ParkingSpot);
    }


    public List<ParkingSpot> findAll(){
        return parkingSpotRepository.findAll();
    }


    public ParkingSpot findParkingSpotByCode(String code) {
        return parkingSpotRepository.findParkingSpotByCod(code);
    }

    @Transactional(readOnly = true)
    public ParkingSpot findAvaliableSpot() {
        return parkingSpotRepository.findFirstByStatus(ParkingStatus.AVAILABLE)
                .orElseThrow(()->new EntityNotFoundException("Doesn't have any spot available"));
    }

}
