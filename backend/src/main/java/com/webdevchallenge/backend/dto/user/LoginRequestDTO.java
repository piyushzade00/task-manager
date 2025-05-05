package com.webdevchallenge.backend.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class LoginRequestDTO {

    @NotBlank(message = "Email cannot be blank.")
    @Email
    private String email;

    @NotBlank(message = "Password cannot be blank.")
    private String password;

    public LoginRequestDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public @NotBlank(message = "Email cannot be blank.") @Email String getEmail() {
        return email;
    }

    public void setEmail(@NotBlank(message = "Email cannot be blank.") @Email String email) {
        this.email = email;
    }

    public @NotBlank(message = "Password cannot be blank.") String getPassword() {
        return password;
    }

    public void setPassword(@NotBlank(message = "Password cannot be blank.") String password) {
        this.password = password;
    }
}
