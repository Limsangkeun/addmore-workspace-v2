package com.addmore.workspace.auth;

import com.addmore.workspace.entity.User;
import com.addmore.workspace.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    public AuthenticationResponse register(RegisterRequest request) {
        User user = User.builder()
                .id(UUID.randomUUID().toString())
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
        return new AuthenticationResponse();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        User loginUser = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new NoSuchElementException("해당 계정의 사용자가 없습니다. : " + request.getUsername()));

        String token = jwtService.generateToken(loginUser);
        return new AuthenticationResponse(token);
    }
}
