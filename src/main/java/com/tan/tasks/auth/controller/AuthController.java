package com.tan.tasks.auth.controller;

import com.tan.tasks.auth.dto.*;
import com.tan.tasks.auth.entity.User;
import com.tan.tasks.auth.security.JwtUtil;
import com.tan.tasks.auth.service.UserService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    public AuthController(
            UserService userService,
            JwtUtil jwtUtil
    ) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(
            @RequestBody AuthRequest request
    ) {
        userService.register(
                request.email(),
                request.password()
        );
        return ResponseEntity.ok("User registered");
    }

    @PostMapping("/login-user")
    public AuthResponse login(
            @RequestBody AuthRequest request
    ) {
        System.out.println("sigin endpoint hit");
        User user = userService.authenticate(
                request.email(),
                request.password()
        );
        return new AuthResponse(jwtUtil.generateToken(user));
    }
}
