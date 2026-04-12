package com.tan.tasks.auth.security;


import com.tan.tasks.auth.entity.AuthProvider;
import com.tan.tasks.auth.entity.Role;
import com.tan.tasks.auth.entity.User;
import com.tan.tasks.auth.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final UserRepository userRepo;
    private final JwtUtil jwtUtil;

    public OAuth2SuccessHandler(UserRepository userRepo, JwtUtil jwtUtil) {
        this.userRepo = userRepo;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException {

        OAuth2User oAuthUser = (OAuth2User) authentication.getPrincipal();
        String email = oAuthUser.getAttribute("email");

        User user = userRepo.findByEmail(email)
                .orElseGet(() -> {
                    User u = new User();
                    u.setEmail(email);
                    u.setPassword(null);
                    u.setRole(Role.USER);
                    u.setProvider(AuthProvider.GOOGLE);
                    return userRepo.save(u);
                });

        String token = jwtUtil.generateToken(user);

        response.setContentType("application/json");
        response.getWriter().write("""
            {
              "token": "%s"
            }
            """.formatted(token));
    }
}