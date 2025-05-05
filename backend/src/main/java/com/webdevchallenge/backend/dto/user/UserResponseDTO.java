package com.webdevchallenge.backend.dto.user;


import java.time.LocalDateTime;

public class UserResponseDTO {

    private Long userId;
    private String userName;
    private String email;
    private String profilePhotoPath;
    private String jwtToken;
    private LocalDateTime createdAt;

    public UserResponseDTO(Long userId, String userName, String email, String profilePhotoPath, String jwtToken, LocalDateTime createdAt) {
        this.userId = userId;
        this.userName = userName;
        this.email = email;
        this.profilePhotoPath = profilePhotoPath;
        this.jwtToken = jwtToken;
        this.createdAt = createdAt;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfilePhotoPath() {
        return profilePhotoPath;
    }

    public void setProfilePhotoPath(String profilePhotoPath) {
        this.profilePhotoPath = profilePhotoPath;
    }

    public String getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
