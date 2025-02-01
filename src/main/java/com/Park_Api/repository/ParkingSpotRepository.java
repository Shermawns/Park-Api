package com.Park_Api.repository;

import com.Park_Api.entity.ParkingSpot;
import com.Park_Api.entity.enums.ParkingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface ParkingSpotRepository extends JpaRepository<ParkingSpot, Long>{

    ParkingSpot findParkingSpotByCod(String code);

    Optional<ParkingSpot> findFirstByStatus(ParkingStatus parkingStatus);

}
