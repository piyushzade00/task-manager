package com.webdevchallenge.backend.controller;

import com.webdevchallenge.backend.dto.user.LoginRequestDTO;
import com.webdevchallenge.backend.dto.user.SignUpRequestDTO;
import com.webdevchallenge.backend.dto.user.UserResponseDTO;
import com.webdevchallenge.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create-user")
    public ResponseEntity<UserResponseDTO> createUser(@RequestParam String userName,
                                                      @RequestParam String email,
                                                      @RequestParam String password,
                                                      @RequestParam MultipartFile profilePhoto) throws Exception {

        SignUpRequestDTO signUpRequestDTO = new SignUpRequestDTO(userName, email, password, "");

        UserResponseDTO userResponseDTO = userService.createUser(signUpRequestDTO,profilePhoto);

        return ResponseEntity.ok(userResponseDTO);
    }

    @PostMapping("/login-user")
    public ResponseEntity<UserResponseDTO> loginUser(@RequestBody LoginRequestDTO loginRequestDTO) {
        UserResponseDTO userResponseDTO = userService.loginUser(loginRequestDTO);
        return ResponseEntity.ok(userResponseDTO);
    }

    @GetMapping("/get-user-by-userid/{userId}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable Long userId) {
        UserResponseDTO userResponseDTO = userService.getUserById(userId);
        return ResponseEntity.ok(userResponseDTO);
    }

    @GetMapping("/get-user-by-user-email/{userEmail}")
    public ResponseEntity<UserResponseDTO> getUserByEmail(@PathVariable String userEmail) {
        UserResponseDTO userResponseDTO = userService.getUserByEmail(userEmail);
        return ResponseEntity.ok(userResponseDTO);
    }

    @GetMapping("/exists-by-user-email/{userEmail}")
    public ResponseEntity<Boolean> existsByEmail(@PathVariable String userEmail) {
        Boolean exists = userService.existsByEmail(userEmail);
        return ResponseEntity.ok(exists);
    }

    @DeleteMapping("/delete-user-by-email/{userEmail}")
    public ResponseEntity<Boolean> deleteUserByEmail(@PathVariable String userEmail) {
        Boolean isDeleted = userService.deleteUserByEmail(userEmail);
        return ResponseEntity.ok(isDeleted);
    }

    @PostMapping("/logout-user/{userEmail}")
    public ResponseEntity<Boolean> logoutUser(@PathVariable String userEmail) {
        Boolean isLogout = userService.logoutUserByEmail(userEmail);
        return ResponseEntity.ok(isLogout);
    }
}
