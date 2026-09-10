package com.davidcoelho.studymanager.auth.service;

import com.davidcoelho.studymanager.account.entity.User;
import com.davidcoelho.studymanager.auth.dto.LoginRequest;
import com.davidcoelho.studymanager.auth.dto.LoginResponse;
import com.davidcoelho.studymanager.auth.principal.AuthUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;


    public AuthService(
            AuthenticationManager authenticationManager,
            JwtService jwtService
    ) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public LoginResponse login(LoginRequest request) {

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                );

        Authentication authentication =
                authenticationManager.authenticate(authenticationToken);

        AuthUserDetails authUserDetails =
                (AuthUserDetails) authentication.getPrincipal();

        String token = jwtService.generateToken(authUserDetails);

        return new LoginResponse(token);
    }

}
