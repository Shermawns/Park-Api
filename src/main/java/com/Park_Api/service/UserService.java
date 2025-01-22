package com.Park_Api.service;

import com.Park_Api.entity.User;
import com.Park_Api.exceptions.errors.DataIntegrityViolationException;
import com.Park_Api.exceptions.errors.EntityNotFoundException;
import com.Park_Api.exceptions.errors.PasswordInvalidException;
import com.Park_Api.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> findAll(){
        return userRepository.findAll();
    }

    public User findById(Long id){
        return userRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("User %s not found in the system", id))
        );
    }

    public User save(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new DataIntegrityViolationException("The user '" + user.getUsername() + "' is already registered.");
        }
        return userRepository.save(user);
    }


    public User editPassword(Long id, String currentPassword, String newPassword, String confirmPassword){

        User user = findById(id);

        if (!passwordEncoder.matches(currentPassword, user.getPassword())){
            throw new PasswordInvalidException("The current password is wrong");
        }

        if (!newPassword.equals(confirmPassword)){
            throw new PasswordInvalidException("The new password doesn't match with the second password");
        }

        user.setPassword(passwordEncoder.encode(newPassword));

        user.setModificationDate(LocalDate.now());

        return userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }
}
