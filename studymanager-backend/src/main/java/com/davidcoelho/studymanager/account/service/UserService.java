package com.davidcoelho.studymanager.account.service;

import com.davidcoelho.studymanager.account.dto.UpdateUserRequest;
import com.davidcoelho.studymanager.account.dto.UserRequest;
import com.davidcoelho.studymanager.account.dto.UserResponse;
import com.davidcoelho.studymanager.account.entity.User;
import com.davidcoelho.studymanager.account.exception.EmailAlreadyExistsException;
import com.davidcoelho.studymanager.account.mapper.UserMapper;
import com.davidcoelho.studymanager.account.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserService(
            UserRepository userRepository,
            UserMapper userMapper,
            PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public UserResponse addUser(UserRequest request) {
        if (userRepository.findUserByEmail(request.getEmail()).isPresent()) {
            throw new EmailAlreadyExistsException(request.getEmail());
        }

        User user = userMapper.toEntity(request);

        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        User savedUser = userRepository.save(user);

        return userMapper.toResponse(savedUser);
    }

    public UserResponse getCurrentUser() {
        User user = getAuthenticatedUser();

        return userMapper.toResponse(user);
    }

    public UserResponse updateUser(UpdateUserRequest request) {
        User user = getAuthenticatedUser();

        user.setName(request.getName());

        User savedUser = userRepository.save(user);

        return userMapper.toResponse(savedUser);
    }

    public void deleteUser() {
        User user = getAuthenticatedUser();

        userRepository.delete(user);
    }

    private User getAuthenticatedUser() {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        return userRepository.findUserByEmail(authentication.getName())
                .orElseThrow(() ->
                        new RuntimeException("Authenticated user not found")
                );
    }
}