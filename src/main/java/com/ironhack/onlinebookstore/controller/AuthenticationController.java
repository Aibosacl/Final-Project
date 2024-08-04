package com.ironhack.onlinebookstore.controller;

import com.ironhack.onlinebookstore.DTOs.LoginRequestDTO;
import com.ironhack.onlinebookstore.DTOs.UserDTO;
import com.ironhack.onlinebookstore.security.JwtUtil;
import com.ironhack.onlinebookstore.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO loginRequest) {
        try {
            // Authenticate the user's credentials
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

            // Retrieve the user from the database and convert to UserDTO
            UserDTO userDTO = userService.findUserByUsername(loginRequest.getUsername())
                    .orElseThrow(() -> new BadCredentialsException("Invalid username or password"));

            // Generate JWT token
            String token = jwtUtil.generateToken(loginRequest.getUsername());

            // Create a response that includes user details and the token
            Map<String, Object> response = new HashMap<>();
            response.put("user", userDTO);
            response.put("token", token);

            // Return the response
            return ResponseEntity.ok(response);
        } catch (AuthenticationException e) {
            // If authentication fails, return 401 Unauthorized
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
    }
}
