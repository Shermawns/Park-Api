package com.Park_Api.controller;

import com.Park_Api.config.TokenService;
import com.Park_Api.controller.Requests.LoginRequest;
import com.Park_Api.controller.Requests.PasswordRequest;
import com.Park_Api.controller.Requests.UserRequest;
import com.Park_Api.controller.Responses.LoginResponse;
import com.Park_Api.controller.Responses.UserResponse;
import com.Park_Api.entity.User;
import com.Park_Api.mapper.UserMapper;
import com.Park_Api.repository.UserRepository;
import com.Park_Api.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/V1/user")
public class UserController {

    private final UserRepository userRepository;
    private final UserService userService;
    private final UserMapper userMapper;
    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;

    public UserController(UserRepository userRepository, UserService userService, UserMapper userMapper, TokenService tokenService, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.userMapper = userMapper;
        this.tokenService = tokenService;
        this.authenticationManager = authenticationManager;
    }


    @PostMapping(value = "/register")
    public ResponseEntity<UserResponse> create(@Validated @RequestBody UserRequest userRequest) {
        if (this.userRepository.findByUsername(userRequest.username()) != null)
            return ResponseEntity.badRequest().build();

        String encryptedPassword = new BCryptPasswordEncoder().encode(userRequest.password());
        User newUser = new User(userRequest.username(), encryptedPassword, userRequest.role(), userRequest.createdBy());
        User saveUser = this.userRepository.save(newUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(userMapper.toUserResponse(saveUser));
    }


    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Validated LoginRequest data) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.username(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);
        var token = tokenService.generateToken((User)auth.getPrincipal());
        return ResponseEntity.ok(new LoginResponse(token));
    }


    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserResponse>> findAll() {
        List<User> list = userService.findAll();
        return ResponseEntity.ok().body(userMapper.toListResponse(list));
    }


    @GetMapping(value = "/find/{id}")
    @PreAuthorize("hasRole('ADMIN') OR ( hasRole('CLIENT') AND #id == principal.id )")
    public ResponseEntity<UserResponse> findById(@PathVariable Long id) {
        User user = userService.findById(id);
        return ResponseEntity.ok().body(userMapper.toUserResponse(user));
    }


    @PatchMapping(value = "/changePassword/{id}")
    @PreAuthorize("(hasRole('ADMIN') and #id == principal.id) or (hasRole('CLIENT') and #id == principal.id)")
    public ResponseEntity<UserResponse> changePassword(@PathVariable Long id, @RequestBody @Valid PasswordRequest passwordRequest) {
        User user = userService.editPassword(id, passwordRequest.currentPassword(), passwordRequest.newPassword(), passwordRequest.confirmNewPassword());
        return ResponseEntity.ok().body(userMapper.toUserResponse(user));
    }
}
