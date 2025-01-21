package com.Park_Api.Controller;

import com.Park_Api.Controller.Requests.UserRequest;
import com.Park_Api.Controller.Responses.UserResponse;
import com.Park_Api.entity.User;
import com.Park_Api.mapper.UserMapper;
import com.Park_Api.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<UserResponse> create(@RequestBody UserRequest userRequest){

        if (userRequest == null){
            System.out.println("User received as null");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        User newUser = userService.save(userMapper.toUserRequest(userRequest));

        return ResponseEntity.status(HttpStatus.CREATED).body(userMapper.toUserResponse(newUser));
    }
}
