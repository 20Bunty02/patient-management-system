package com.pm.authservice.service;

import com.pm.authservice.dto.LoginRequestDTO;
import com.pm.authservice.model.User;
import com.pm.authservice.util.JwtUtil;
import io.jsonwebtoken.JwtException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final JwtUtil jwtUtil;
    public AuthService(UserService userService,PasswordEncoder passwordEncoder,JwtUtil jwtUtil){
        this.userService=userService;
        this.jwtUtil=jwtUtil;
        this.passwordEncoder=passwordEncoder;
    }
    public Optional<String> authenticate(LoginRequestDTO loginRequestDTO){
        Optional<String> token=userService.findByEmail(loginRequestDTO.getEmail()).
                filter(u-> passwordEncoder.matches(loginRequestDTO.getPassword(),u.getPassword()))
                .map(u->jwtUtil.generateToken(u.getEmail(),u.getRole()));
        return token;
    }

    public boolean validToken(String substring) {
        try{
            jwtUtil.validateToken(substring);
            return true;
        }
        catch (JwtException e){
            return false;
        }
    }
}
