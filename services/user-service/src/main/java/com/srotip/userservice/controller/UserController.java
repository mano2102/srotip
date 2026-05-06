package com.srotip.userservice.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.srotip.userservice.dto.ApiResponse;
import com.srotip.userservice.dto.UserRequestDTO;
import com.srotip.userservice.dto.UserResponseDTO;
import com.srotip.userservice.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ApiResponse<UserResponseDTO> create(@Valid @RequestBody UserRequestDTO request) {
        return new ApiResponse<>(true, "User created successfully", userService.createUser(request));
    }

    @GetMapping
    public ApiResponse<List<UserResponseDTO>> getAll() {
        return new ApiResponse<>(true, "Users fetched successfully", userService.getAllUsers());
    }

    @GetMapping("/{id}")
    public ApiResponse<UserResponseDTO> getById(@PathVariable Long id) {
        return new ApiResponse<>(true, null, userService.getUserById(id));
    }

    @PutMapping("/{id}")
    public ApiResponse<UserResponseDTO> update(@PathVariable Long id,
            @Valid @RequestBody UserRequestDTO request) {
        return new ApiResponse<>(true, "User updated", userService.updateUser(id, request));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> delete(@PathVariable Long id) {
        userService.deleteUser(id);
        return new ApiResponse<>(true, "User deleted", null);
    }

}
