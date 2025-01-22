package com.Park_Api.service;

import com.Park_Api.entity.User;
import com.Park_Api.exceptions.errors.DataIntegrityViolationException;
import com.Park_Api.exceptions.errors.EntityNotFoundException;
import com.Park_Api.exceptions.errors.PasswordInvalidException;
import com.Park_Api.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

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
        if (userRepository.findByUsername(user.getUsername()).isPresent()){
            throw new DataIntegrityViolationException("The user " + user.getUsername() + " is already registered");
        }
        return userRepository.save(user);
    }

    public User editPassword(Long id, String currentPassword, String newPassword, String confirmPassword){

        User user = findById(id);

        if (!currentPassword.equals(user.getPassword())){
            throw new PasswordInvalidException("The current password is wrong");
        }

        if (!newPassword.equals(confirmPassword)){
            throw new PasswordInvalidException("The new password doesn't match with the second password");
        }

        user.setPassword(newPassword);

        user.setModificationDate(LocalDate.now());

        return userRepository.save(user);
    }
}
