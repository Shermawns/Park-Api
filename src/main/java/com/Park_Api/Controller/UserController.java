package com.Park_Api.Controller;

import com.Park_Api.entity.User;
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

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/create")
    public ResponseEntity<User> create(@RequestBody User user){

        if (user == null){
            System.out.println("User received as null");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        User newUser = userService.save(user);

        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }
}
