package com.webdevchallenge.backend.service;

import com.webdevchallenge.backend.dto.user.SignUpRequestDTO;
import com.webdevchallenge.backend.dto.user.UserResponseDTO;

public interface UserService {
    UserResponseDTO createUser(SignUpRequestDTO signUpRequestDTO);

    UserResponseDTO getUserById(Long userId);

    UserResponseDTO getUserByEmail(String email);

    boolean existsByEmail(String email);

    boolean deleteUserByEmail(String email);
}
