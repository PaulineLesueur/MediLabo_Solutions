package com.openclassrooms.ms_api_gateway.controller;

import com.openclassrooms.ms_api_gateway.auth.JwtTokenUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final JwtTokenUtil jwtTokenUtil;
    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder passwordEncoder;

    public AuthController(JwtTokenUtil jwtTokenUtil, UserDetailsService userDetailsService, BCryptPasswordEncoder passwordEncoder) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
        String username = credentials.get("username");
        String password = credentials.get("password");

        System.out.println("Attempting login for user: " + username);

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        if(passwordEncoder.matches(password, userDetails.getPassword())) {
            String token = jwtTokenUtil.generateToken(username, userDetails.getAuthorities().iterator().next().getAuthority());
            System.out.println("Generated token: " + token);
            return ResponseEntity.ok(Map.of("token", token));
        }
        return ResponseEntity.status(401).body("Invalid credentials");
    }
}
