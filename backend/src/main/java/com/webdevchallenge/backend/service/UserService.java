package com.webdevchallenge.backend.service;

import com.webdevchallenge.backend.dto.user.LoginRequestDTO;
import com.webdevchallenge.backend.dto.user.SignUpRequestDTO;
import com.webdevchallenge.backend.dto.user.UserResponseDTO;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {
    UserResponseDTO createUser(SignUpRequestDTO signUpRequestDTO, MultipartFile profilePhoto) throws Exception;

    UserResponseDTO loginUser(LoginRequestDTO loginRequestDTO);

    UserResponseDTO getUserById(Long userId);

    UserResponseDTO getUserByEmail(String email);

    boolean existsByEmail(String email);

    boolean deleteUserByEmail(String email);

    boolean logoutUserByEmail(String email);
}
