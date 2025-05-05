package com.webdevchallenge.backend.service.impl;

import com.webdevchallenge.backend.dto.user.SignUpRequestDTO;
import com.webdevchallenge.backend.dto.user.UserResponseDTO;
import com.webdevchallenge.backend.entity.UserEntity;
import com.webdevchallenge.backend.mapper.UserMapper;
import com.webdevchallenge.backend.repository.UserRepository;
import com.webdevchallenge.backend.service.JwtService;
import com.webdevchallenge.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final JwtService jwtService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, JwtService jwtService) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.jwtService = jwtService;
    }

    @Override
    public UserResponseDTO createUser(SignUpRequestDTO signUpRequestDTO) {
        if(signUpRequestDTO == null) {
            throw new IllegalArgumentException("User data not present.");
        }

        String userName = signUpRequestDTO.getUserName();
        if(!StringUtils.hasText(userName)) {
            throw new IllegalArgumentException("User name must not be blank.");
        }

        String email = signUpRequestDTO.getEmail();
        if(!StringUtils.hasText(email)) {
            throw new IllegalArgumentException("User email must not be blank.");
        }

        email = email.trim().toLowerCase();

        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email is already registered.");
        }

        UserEntity userEntity = userMapper.toSignUpEntity(signUpRequestDTO);

        String token = jwtService.generateToken(userEntity);

        userEntity.setJwtToken(token);

        UserEntity savedUser = userRepository.save(userEntity);
        return userMapper.toUserResponseDTO(savedUser,token);
    }

    @Override
    public UserResponseDTO getUserById(Long userId) {
        if(userId == null) {
            throw new IllegalArgumentException("User data not present.");
        }

        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found."));

        return userMapper.toUserResponseDTO(userEntity,userEntity.getJwtToken());
    }

    @Override
    public UserResponseDTO getUserByEmail(String email) {
        if(!StringUtils.hasText(email)) {
            throw new IllegalArgumentException("User email must not be blank.");
        }

        email = email.trim().toLowerCase();

        UserEntity userEntity = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found."));

        return userMapper.toUserResponseDTO(userEntity,userEntity.getJwtToken());
    }

    @Override
    public boolean existsByEmail(String email) {
        if(!StringUtils.hasText(email)) {
            throw new IllegalArgumentException("User email must not be blank.");
        }

        email = email.trim().toLowerCase();

        return userRepository.existsByEmail(email);
    }

    @Override
    public boolean deleteUserByEmail(String email) {
        if(!StringUtils.hasText(email)) {
            throw new IllegalArgumentException("User email must not be blank.");
        }

        email = email.trim().toLowerCase();

        UserEntity userEntity = userRepository.findByEmail(email)
            .orElseThrow(() -> new IllegalArgumentException("User not found."));

        userRepository.delete(userEntity);
        return true;
    }
}
