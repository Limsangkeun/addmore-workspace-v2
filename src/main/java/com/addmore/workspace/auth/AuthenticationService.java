package com.addmore.workspace.auth;

import com.addmore.workspace.entity.User;
import com.addmore.workspace.repository.UserRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
@Log4j2
public class AuthenticationService {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        User user = User.builder()
                .id(UUID.randomUUID().toString())
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
        return new AuthenticationResponse();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        log.info("***** login attempt*****");
        log.info(request);
        log.info(passwordEncoder.encode(request.getPassword()));

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        User loginUser = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new NoSuchElementException("해당 계정의 사용자가 없습니다. : " + request.getUsername()));

        String token = jwtService.generateToken(loginUser);
        return AuthenticationResponse.builder()
                .username(loginUser.getUsername())
                .name(loginUser.getName())
                .token(token).build();
    }

    public Map<String, Object> isvalidToken(AuthenticationRequest request) {
        Map<String, Object> resultMap = new HashMap<>();
        String username = jwtService.extractUsername(request.getToken());
        resultMap.put("isValid", jwtService.isTokenValid(request.getToken(), userRepository.findByUsername(username).orElseThrow()));
        return resultMap;
    }
}
