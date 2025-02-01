package com.Park_Api.repository;

import com.Park_Api.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Client, Long> {
    Client findByUserId(Long id);
    Client findByCpf(String clientCpf);

}