package com.example.productapi.service;

import com.example.productapi.dto.AuthResponse;
import com.example.productapi.dto.LoginRequest;
import com.example.productapi.dto.RegisterRequest;
import com.example.productapi.entity.User;
import com.example.productapi.repository.RefreshTokenRepository;
import com.example.productapi.repository.UserRepository;
import com.example.productapi.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.productapi.entity.RefreshToken;
import com.example.productapi.repository.RefreshTokenRepository;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final RefreshTokenRepository refreshTokenRepository;

    public void register(RegisterRequest request) {

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new IllegalArgumentException("Username already exists");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole("USER");

        userRepository.save(user);
    }
    
    

    public AuthResponse login(LoginRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        String accessToken = jwtService.generateToken(
                user.getUsername(),
                user.getRole()
        );

        String refreshTokenValue =
                jwtService.generateRefreshToken(user.getUsername());

        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken(refreshTokenValue);
        refreshToken.setUser(user);
        refreshToken.setExpiresAt(
                LocalDateTime.now().plusDays(7)
        );

        refreshTokenRepository.save(refreshToken);

        return new AuthResponse(
                accessToken,
                refreshTokenValue
        );
    }
    public AuthResponse refresh(String token) {

        RefreshToken refreshToken = refreshTokenRepository
                .findByToken(token)
                .orElseThrow(() ->
                        new IllegalArgumentException("Invalid refresh token"));

        if (refreshToken.getExpiresAt().isBefore(LocalDateTime.now())) {
            refreshTokenRepository.delete(refreshToken);
            throw new IllegalArgumentException("Refresh token expired");
        }

        User user = refreshToken.getUser();

        String accessToken = jwtService.generateToken(
                user.getUsername(),
                user.getRole()
        );

        refreshTokenRepository.delete(refreshToken);

        String newRefreshTokenValue =
                jwtService.generateRefreshToken(user.getUsername());

        RefreshToken newRefreshToken = new RefreshToken();
        newRefreshToken.setToken(newRefreshTokenValue);
        newRefreshToken.setUser(user);
        newRefreshToken.setExpiresAt(
                LocalDateTime.now().plusDays(7)
        );

        refreshTokenRepository.save(newRefreshToken);

        return new AuthResponse(
                accessToken,
                newRefreshTokenValue
        );
    }
    
}