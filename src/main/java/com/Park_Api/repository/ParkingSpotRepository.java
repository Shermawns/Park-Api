package com.Park_Api.repository;

import com.Park_Api.entity.ParkingSpot;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParkingSpotRepository extends JpaRepository<ParkingSpot, Long>{

    ParkingSpot findParkingSpotByCod(String code);
}
