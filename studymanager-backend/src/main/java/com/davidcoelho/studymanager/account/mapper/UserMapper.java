package com.davidcoelho.studymanager.account.mapper;

import com.davidcoelho.studymanager.account.dto.UserRequest;
import com.davidcoelho.studymanager.account.dto.UserResponse;
import com.davidcoelho.studymanager.account.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User toEntity(UserRequest request){
        return new User(
                request.getName(),
                request.getEmail(),
                request.getPassword()
        );
    }
    public UserResponse toResponse(User user){
        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail()
        );
    }
}
