package com.webdevchallenge.backend.service;

import com.webdevchallenge.backend.entity.UserEntity;

import java.security.Key;

public interface JwtService {

    String generateToken(UserEntity user);

    boolean validateToken(String token);

    String extractUsername(String token);
}
