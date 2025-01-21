package com.Park_Api.repository;

import com.Park_Api.controller.Requests.UserRequest;
import com.Park_Api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String name);
}
