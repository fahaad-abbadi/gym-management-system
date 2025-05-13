package com.example.advanced_mappings.controllers;

import com.example.advanced_mappings.dtos.LoginRequest;
import com.example.advanced_mappings.dtos.RegisterRequest;
import com.example.advanced_mappings.dtos.Response;
import com.example.advanced_mappings.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AuthController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<Response> registerUser(@Valid @RequestBody RegisterRequest registerRequest) {
        return ResponseEntity.ok(userService.registerUser(registerRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<Response> loginUser(@Valid @RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(userService.loginUser(loginRequest));
    }
}
