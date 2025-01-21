package com.Park_Api.mapper;

import com.Park_Api.Controller.Requests.UserRequest;
import com.Park_Api.Controller.Responses.PasswordResponse;
import com.Park_Api.Controller.Responses.UserResponse;
import com.Park_Api.entity.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapper {

    public User toUserRequest(UserRequest userRequest){
        return new User(
                userRequest.username(),
                userRequest.password(),
                userRequest.role(),
                userRequest.createdBy()
        );
    }

    public UserResponse toUserResponse(User user){
        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getRole(),
                user.getCreatedDate(),
                user.getModificationDate(),
                user.getCreatedBy(),
                user.getUpdateBy()
        );
    }

    public List<UserResponse> toListResponse(List<User> users){
        return users
                .stream()
                .map(user -> new UserResponse(
                        user.getId(),
                        user.getUsername(),
                        user.getRole(),
                        user.getCreatedDate(),
                        user.getModificationDate(),
                        user.getCreatedBy(),
                        user.getUpdateBy()))
                .collect(Collectors.toList());
    }

    public PasswordResponse toPassword (User user){
        return new PasswordResponse(user.getPassword());
    }
}
