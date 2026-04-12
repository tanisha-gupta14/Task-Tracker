package com.tan.tasks.auth.service;

import com.tan.tasks.auth.entity.AuthProvider;
import com.tan.tasks.auth.entity.Role;
import com.tan.tasks.auth.entity.User;
import com.tan.tasks.auth.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository repo;
    private final PasswordEncoder encoder;

    public UserService(UserRepository repo, PasswordEncoder encoder) {
        this.repo = repo;
        this.encoder = encoder;
    }

    public User register(String email, String password) {

        if (password == null || password.isBlank()) {
            throw new RuntimeException("Password is required");
        }

        repo.findByEmail(email).ifPresent(u -> {
            if (u.getProvider() != AuthProvider.LOCAL) {
                throw new RuntimeException("Email registered via Google");
            }
            throw new RuntimeException("Email already exists");
        });

        User user = new User();
        user.setEmail(email);
        user.setPassword(encoder.encode(password));
        user.setRole(Role.USER);
        user.setProvider(AuthProvider.LOCAL);

        return repo.save(user);
    }

    public User authenticate(String email, String password) {

        User user = repo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));
        if (user.getProvider() != AuthProvider.LOCAL) {
            throw new RuntimeException("Use Google login");
        }
        if (user.getPassword() == null || !encoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        return user;
    }
}