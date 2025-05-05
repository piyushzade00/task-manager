package com.webdevchallenge.backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @NotNull(message = "Username is required.")
    @Size(min = 4, max = 50, message = "Username must be between 4 and 50 characters.")
    @Column(name= "user_name", nullable = false)
    private String userName;

    @Column(name= "email",unique = true, nullable = false)
    @NotNull(message = "Email is required.")
    @Email(message = "Please provide a valid email address.")
    private String email;

    @NotNull(message = "Password is required.")
    @Column(name= "password", nullable = false)
    private String password;

    @Column(name = "profile_photo_path")
    private String profilePhotoPath;

    @NotNull
    @Column(name= "jwt_token")
    private String jwtToken;

    @CreationTimestamp
    @Column(name="created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name="updated_at")
    private LocalDateTime updatedAt;

    // One User can have multiple Tasks
    @OneToMany(mappedBy = "createdBy", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<TaskEntity> tasks;

    public UserEntity() {}

    public UserEntity(Long userId, String userName, String email, String password, String profilePhotoPath, String jwtToken, LocalDateTime createdAt, LocalDateTime updatedAt, List<TaskEntity> tasks) {
        this.userId = userId;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.profilePhotoPath = profilePhotoPath;
        this.jwtToken = jwtToken;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.tasks = tasks;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public @NotNull(message = "Username is required.") @Size(min = 4, max = 50, message = "Username must be between 4 and 50 characters.") String getUserName() {
        return userName;
    }

    public void setUserName(@NotNull(message = "Username is required.") @Size(min = 4, max = 50, message = "Username must be between 4 and 50 characters.") String userName) {
        this.userName = userName;
    }

    public @NotNull(message = "Email is required.") @Email(message = "Please provide a valid email address.") String getEmail() {
        return email;
    }

    public void setEmail(@NotNull(message = "Email is required.") @Email(message = "Please provide a valid email address.") String email) {
        this.email = email;
    }

    public @NotNull(message = "Password is required.") String getPassword() {
        return password;
    }

    public void setPassword(@NotNull(message = "Password is required.") String password) {
        this.password = password;
    }

    public String getProfilePhotoPath() {
        return profilePhotoPath;
    }

    public void setProfilePhotoPath(String profilePhotoPath) {
        this.profilePhotoPath = profilePhotoPath;
    }

    public @NotNull String getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(@NotNull String jwtToken) {
        this.jwtToken = jwtToken;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<TaskEntity> getTasks() {
        return tasks;
    }

    public void setTasks(List<TaskEntity> tasks) {
        this.tasks = tasks;
    }
}
