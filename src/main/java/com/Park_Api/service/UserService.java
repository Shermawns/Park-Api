package com.Park_Api.service;

import com.Park_Api.entity.User;
import com.Park_Api.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findAll(){
        return userRepository.findAll();
    }

    public User findById(Long id){
        return userRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("User %s not found in the system", id))
        );
    }

    public User save(User user){
        return userRepository.save(user);
    }

    public User editPassword(Long id, String currentPassword, String newPassword, String confirmPassword){

        User user = findById(id);

        if (!currentPassword.equals(user.getPassword())){
            System.out.println("The passwords are wrong!");
        }

        if (!newPassword.equals(confirmPassword)){
            System.out.println("The first password doesn't match with the second password");
        }

        user.setPassword(newPassword);

        return save(user);
    }
}
