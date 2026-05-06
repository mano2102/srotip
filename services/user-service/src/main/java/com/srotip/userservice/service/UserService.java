package com.srotip.userservice.service;

import java.util.List;

import com.srotip.userservice.dto.UserRequestDTO;
import com.srotip.userservice.dto.UserResponseDTO;

public interface UserService {
    UserResponseDTO createUser(UserRequestDTO user);

    List<UserResponseDTO> getAllUsers();

    UserResponseDTO getUserById(Long id);

    UserResponseDTO updateUser(Long id, UserRequestDTO user);

    void deleteUser(Long id);
}
