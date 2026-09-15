package com.orbitbase.controller;

import com.orbitbase.model.Role;
import com.orbitbase.model.User;
import com.orbitbase.repository.UserRepository;
import com.orbitbase.security.JwtTokenProvider;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Register and login to get a JWT token")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Operation(summary = "Register a new user", description = "Creates a VIEWER account and returns a JWT token")
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        User user = new User();
        user.setUsername(request.username());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setEmail(request.email());
        user.setRole(Role.VIEWER);
        userRepository.save(user);
        return ResponseEntity.ok(new AuthResponse(jwtTokenProvider.generateToken(user.getUsername())));
    }

    @Operation(summary = "Login", description = "Authenticate with username/password and receive a JWT token")
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.username(), request.password()));
        return ResponseEntity.ok(new AuthResponse(jwtTokenProvider.generateToken(request.username())));
    }

    record LoginRequest(String username, String password) {}
    record RegisterRequest(String username, String password, String email) {}
    record AuthResponse(String token) {}
}
