package com.davidcoelho.studymanager.account.service;

import com.davidcoelho.studymanager.account.dto.UpdateUserRequest;
import com.davidcoelho.studymanager.account.dto.UserRequest;
import com.davidcoelho.studymanager.account.dto.UserResponse;
import com.davidcoelho.studymanager.account.entity.User;
import com.davidcoelho.studymanager.account.exception.EmailAlreadyExistsException;
import com.davidcoelho.studymanager.account.exception.UserNotFoundException;
import com.davidcoelho.studymanager.account.mapper.UserMapper;
import com.davidcoelho.studymanager.account.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService( UserRepository userRepository, UserMapper userMapper){
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public UserResponse addUser(UserRequest request){
        if(userRepository.findUserByEmail(request.getEmail()).isPresent()){
            throw new EmailAlreadyExistsException(request.getEmail());
        }
        User user = userMapper.toEntity(request);
        User savedUser = userRepository.save(user);
        return userMapper.toResponse(savedUser);
    }

    public List<UserResponse> listUsers(){
        return userRepository.findAll()
                .stream()
                .map(userMapper::toResponse)
                .toList();
    }

    public UserResponse getUserById(Integer id){
        User user = findUserByIdOrThrow(id);
        return userMapper.toResponse(user);
    }

    public UserResponse findUserByEmail(String email){
        return userRepository.findUserByEmail(email)
                .map(userMapper::toResponse)
                .orElseThrow(()-> new UserNotFoundException(email));
    }

    public UserResponse updateUser(Integer id, UpdateUserRequest request){
        User userFound = findUserByIdOrThrow(id);
        userFound.setName(request.getName());
        User savedUser = userRepository.save(userFound);

        return userMapper.toResponse(savedUser);
    }

    public void deleteUser(Integer id){
        User userFound = findUserByIdOrThrow(id);
        userRepository.delete(userFound);
    }

    private User findUserByIdOrThrow(Integer id){
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

}
