package com.webdevchallenge.backend.service.impl;

import com.webdevchallenge.backend.dto.user.SignUpRequestDTO;
import com.webdevchallenge.backend.dto.user.UserResponseDTO;
import com.webdevchallenge.backend.mapper.UserMapper;
import com.webdevchallenge.backend.repository.UserRepository;
import com.webdevchallenge.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserResponseDTO createUser(SignUpRequestDTO signUpRequestDTO) {
        return null;
    }

    @Override
    public UserResponseDTO getUserById(Long userId) {
        return null;
    }

    @Override
    public UserResponseDTO getUserByEmail(String email) {
        return null;
    }

    @Override
    public boolean existsByEmail(String email) {
        return false;
    }

    @Override
    public boolean deactivateUserByEmail(String email) {
        return false;
    }
}
