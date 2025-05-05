package com.webdevchallenge.backend.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class SignUpRequestDTO {

    @NotBlank(message = "Username cannot be blank.")
    private String userName;

    @NotBlank(message = "Email cannot be blank.")
    @Email
    private String email;

    @NotBlank(message = "Password cannot be blank.")
    @Size(min = 8, max = 20, message = "Password must be at least 8 and less than 20 characters.")
    private String password;

    private String profilePhotoPath;

    public SignUpRequestDTO(String userName, String email, String password, String profilePhotoPath) {
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.profilePhotoPath = profilePhotoPath;
    }

    public @NotBlank(message = "Username cannot be blank.") String getUserName() {
        return userName;
    }

    public void setUserName(@NotBlank(message = "Username cannot be blank.") String userName) {
        this.userName = userName;
    }

    public @NotBlank(message = "Email cannot be blank.") @Email String getEmail() {
        return email;
    }

    public void setEmail(@NotBlank(message = "Email cannot be blank.") @Email String email) {
        this.email = email;
    }

    public @NotBlank(message = "Password cannot be blank.") @Size(min = 8, max = 20, message = "Password must be at least 8 and less than 20 characters.") String getPassword() {
        return password;
    }

    public void setPassword(@NotBlank(message = "Password cannot be blank.") @Size(min = 8, max = 20, message = "Password must be at least 8 and less than 20 characters.") String password) {
        this.password = password;
    }

    public String getProfilePhotoPath() {
        return profilePhotoPath;
    }

    public void setProfilePhotoPath(String profilePhotoPath) {
        this.profilePhotoPath = profilePhotoPath;
    }
}
