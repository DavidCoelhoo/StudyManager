package com.davidcoelho.studymanager.account.controller;

import com.davidcoelho.studymanager.account.dto.UpdateUserRequest;
import com.davidcoelho.studymanager.account.dto.UserRequest;
import com.davidcoelho.studymanager.account.dto.UserResponse;
import com.davidcoelho.studymanager.account.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserResponse> add (@Valid @RequestBody UserRequest request){
        UserResponse response = userService.addUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public List<UserResponse> getUsers(){
        return userService.listUsers();
    }

    @GetMapping("/{id}")
    public UserResponse getUserById(@PathVariable Integer id){
        return userService.getUserById(id);
    }

    @GetMapping(params = "email")
    public UserResponse getByEmail(@RequestParam String email){
        return userService.findUserByEmail(email);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable Integer id, @Valid @RequestBody UpdateUserRequest request){
        UserResponse response = userService.updateUser(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id){
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
