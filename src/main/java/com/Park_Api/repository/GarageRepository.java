package com.Park_Api.repository;

import com.Park_Api.entity.Client;
import com.Park_Api.entity.Garage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GarageRepository extends JpaRepository<Garage, Long> {
}
