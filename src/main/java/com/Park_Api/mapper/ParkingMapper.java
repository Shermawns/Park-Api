package com.Park_Api.mapper;

import com.Park_Api.controller.Requests.ParkingRequest;
import com.Park_Api.controller.Responses.ParkingResponse;
import com.Park_Api.entity.ParkingSpot;
import org.springframework.stereotype.Component;

@Component
public class ParkingMapper {

    public ParkingSpot toParking(ParkingRequest parkingRequest){
        return new ParkingSpot(
                parkingRequest.code(),
                parkingRequest.parkingStatus()
        );
    }



    public ParkingResponse toResponse(ParkingSpot parkingSpot){
        return new ParkingResponse(
                parkingSpot.getId(),
                parkingSpot.getCod(),
                parkingSpot.getStatus()
        );
    }
}
