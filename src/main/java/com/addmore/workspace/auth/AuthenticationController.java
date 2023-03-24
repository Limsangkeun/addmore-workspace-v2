package com.addmore.workspace.auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@Log4j2
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final PasswordEncoder passwordEncoder;

    @RequestMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @RequestMapping("/loginProcess")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request){
        log.info("***** login attempt*****");
        log.info(request);
        log.info(passwordEncoder.encode(request.getPassword()));
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }
}
