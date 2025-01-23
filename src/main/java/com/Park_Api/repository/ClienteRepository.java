package com.Park_Api.repository;

import com.Park_Api.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Client, Long> {
    Client findUserById(Long id);
}
