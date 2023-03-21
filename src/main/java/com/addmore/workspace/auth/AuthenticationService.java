package com.addmore.workspace.auth;

import com.addmore.workspace.common.entity.User;
import com.addmore.workspace.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public AuthenticationResponse register(RegisterRequest request) {
        User user = User.builder()
                .id(request.getId())
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
        return new AuthenticationResponse();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        return null;
    }
}
