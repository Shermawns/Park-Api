package com.Park_Api.mapper;

import com.Park_Api.Controller.Requests.UserRequest;
import com.Park_Api.Controller.Responses.UserResponse;
import com.Park_Api.entity.User;
import org.springframework.stereotype.Component;

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
}
