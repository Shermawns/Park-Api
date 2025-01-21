package com.Park_Api.controller;

import com.Park_Api.controller.Requests.PasswordRequest;
import com.Park_Api.controller.Requests.UserRequest;
import com.Park_Api.controller.Responses.UserResponse;
import com.Park_Api.entity.User;
import com.Park_Api.mapper.UserMapper;
import com.Park_Api.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/V1/user")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @PostMapping(value = "/create")
    public ResponseEntity<UserResponse> create(@Valid @RequestBody UserRequest userRequest){

        User newUser = userService.save(userMapper.toUserRequest(userRequest));

        return ResponseEntity.status(HttpStatus.CREATED).body(userMapper.toUserResponse(newUser));
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> findAll(){

        List<User> list = userService.findAll();

        return ResponseEntity.ok().body(userMapper.toListResponse(list));
    }

    @GetMapping(value = "/find/{id}")
    public ResponseEntity<UserResponse> findById(@PathVariable Long id){

        User user = userService.findById(id);

        return ResponseEntity.ok().body(userMapper.toUserResponse(user));
    }

    @PatchMapping(value = "/changePassword/{id}")
    public ResponseEntity<UserResponse> changePassword(@PathVariable Long id, @RequestBody @Valid PasswordRequest passwordRequest){

        User user = userService.editPassword(id, passwordRequest.currentPassword(), passwordRequest.newPassword(), passwordRequest.confirmNewPassword());

        return ResponseEntity.ok().body(userMapper.toUserResponse(user));
    }

}
