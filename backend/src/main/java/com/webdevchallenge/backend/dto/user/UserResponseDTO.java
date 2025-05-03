package com.webdevchallenge.backend.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDTO {

    private Long userId;
    private String userName;
    private String email;
    private String profilePhotoPath;
    private String jwtToken;
    private LocalDateTime createdAt;
}
