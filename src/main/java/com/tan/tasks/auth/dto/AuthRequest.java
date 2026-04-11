package com.tan.tasks.auth.dto;

public record AuthRequest(
        String email,
        String password
) {
}
