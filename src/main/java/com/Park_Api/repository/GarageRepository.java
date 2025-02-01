package com.Park_Api.repository;

import com.Park_Api.entity.Client;
import com.Park_Api.entity.Garage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface GarageRepository extends JpaRepository<Garage, Long> {
    Garage findByReceipt(String receipt);
    List<Garage> findByClientCpf(String cpf);
}
