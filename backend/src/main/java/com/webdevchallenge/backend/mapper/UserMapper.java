package com.webdevchallenge.backend.mapper;

import com.webdevchallenge.backend.dto.user.LoginRequestDTO;
import com.webdevchallenge.backend.dto.user.SignUpRequestDTO;
import com.webdevchallenge.backend.dto.user.UserResponseDTO;
import com.webdevchallenge.backend.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserEntity toLoginEntity(LoginRequestDTO loginRequestDTO) {
        if(loginRequestDTO == null) {
            return null;
        }
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(loginRequestDTO.getEmail());
        userEntity.setPassword(loginRequestDTO.getPassword());

        return userEntity;
    }

    public UserEntity toSignUpEntity(SignUpRequestDTO signUpRequestDTO) {
        if(signUpRequestDTO == null) {
            return null;
        }
        UserEntity userEntity = new UserEntity();

        userEntity.setUserName(signUpRequestDTO.getUserName());
        userEntity.setEmail(signUpRequestDTO.getEmail());
        userEntity.setPassword(signUpRequestDTO.getPassword());
        userEntity.setProfilePhotoPath(signUpRequestDTO.getProfilePhotoPath());

        return userEntity;
    }

    public UserResponseDTO toUserResponseDTO(UserEntity userEntity, String jwtToken) {
        if(userEntity == null) {
            return null;
        }

        return new UserResponseDTO(
                userEntity.getUserId(),
                userEntity.getUserName(),
                userEntity.getEmail(),
                userEntity.getProfilePhotoPath(),
                jwtToken,
                userEntity.getCreatedAt()
        );  // Replace with JWT Token
    }
}
