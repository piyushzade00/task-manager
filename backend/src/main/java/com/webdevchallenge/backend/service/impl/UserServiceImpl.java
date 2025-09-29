package com.webdevchallenge.backend.service.impl;

import com.webdevchallenge.backend.dto.user.LoginRequestDTO;
import com.webdevchallenge.backend.dto.user.SignUpRequestDTO;
import com.webdevchallenge.backend.dto.user.UserResponseDTO;
import com.webdevchallenge.backend.entity.UserEntity;
import com.webdevchallenge.backend.mapper.UserMapper;
import com.webdevchallenge.backend.repository.UserRepository;
import com.webdevchallenge.backend.service.JwtService;
import com.webdevchallenge.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final JwtService jwtService;
    PasswordEncoder passwordEncoder;

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, JwtService jwtService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserResponseDTO createUser(SignUpRequestDTO signUpRequestDTO, MultipartFile profilePhoto) throws IOException {
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

        String fileName = null;
        String fullPath = null;

        if (!profilePhoto.isEmpty()) {
            fileName = System.currentTimeMillis() + "_" + profilePhoto.getOriginalFilename();
            fullPath = uploadDir + fileName;

            Path path = Paths.get(fullPath);
            Files.createDirectories(path.getParent());
            Files.copy(profilePhoto.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

            signUpRequestDTO.setProfilePhotoPath(fileName);
        }

        UserEntity userEntity = userMapper.toSignUpEntity(signUpRequestDTO);

        String jwtToken = jwtService.generateToken(userEntity);
        userEntity.setJwtToken(jwtToken);

        UserEntity savedUser = null;
        try {
            savedUser = userRepository.save(userEntity);
        } catch (Exception e) {
            if (fullPath != null) {
                try {
                    Files.deleteIfExists(Paths.get(fullPath));
                } catch (IOException ioEx) {
                    System.err.println("Failed to delete orphan profile image: " + ioEx.getMessage());
                }
            }
            throw new RuntimeException("User save failed: " + e.getMessage(), e);
        }
        return userMapper.toUserResponseDTO(savedUser,jwtToken);
    }

    @Override
    public UserResponseDTO loginUser(LoginRequestDTO loginRequestDTO) {
        if(loginRequestDTO == null) {
            throw new IllegalArgumentException("User data not present.");
        }

        if(!StringUtils.hasText(loginRequestDTO.getEmail()) || !StringUtils.hasText(loginRequestDTO.getPassword())) {
            throw new IllegalArgumentException("User data not present.");
        }

        UserEntity userEntity = userRepository.findByEmail(loginRequestDTO.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("User not found."));

        if (!passwordEncoder.matches(loginRequestDTO.getPassword(), userEntity.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        String jwtToken = jwtService.generateToken(userEntity);

        userEntity.setJwtToken(jwtToken);
        UserEntity savedUser = userRepository.save(userEntity);

        return userMapper.toUserResponseDTO(savedUser,jwtToken);
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

    @Override
    public boolean logoutUserByEmail(String email){
        if(!StringUtils.hasText(email)) {
            throw new IllegalArgumentException("User email must not be blank.");
        }
        email = email.trim().toLowerCase();

        UserEntity userEntity = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found."));

        userEntity.setJwtToken(null);

        userRepository.save(userEntity);

        return true;
        // This is for testing 13
    }
}
