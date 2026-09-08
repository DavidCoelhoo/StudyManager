package com.davidcoelho.studymanager.account.controller;

import com.davidcoelho.studymanager.account.dto.UpdateUserRequest;
import com.davidcoelho.studymanager.account.dto.UserRequest;
import com.davidcoelho.studymanager.account.dto.UserResponse;
import com.davidcoelho.studymanager.account.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserResponse> add(
            @Valid @RequestBody UserRequest request
    ){
        UserResponse response = userService.addUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/me")
    public UserResponse getCurrentUser(){
        return userService.getCurrentUser();
    }

    @PatchMapping("/me")
    public ResponseEntity<UserResponse> updateUser(
            @Valid @RequestBody UpdateUserRequest request
    ){
        UserResponse response = userService.updateUser(request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/me")
    public ResponseEntity<Void> deleteUser(){
        userService.deleteUser();
        return ResponseEntity.noContent().build();
    }
}