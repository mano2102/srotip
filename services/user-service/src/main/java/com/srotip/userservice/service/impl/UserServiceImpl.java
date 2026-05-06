package com.srotip.userservice.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.srotip.userservice.dto.UserRequestDTO;
import com.srotip.userservice.dto.UserResponseDTO;
import com.srotip.userservice.event.UserCreatedEvent;
import com.srotip.userservice.exception.ResourceNotFoundException;
import com.srotip.userservice.model.User;
import com.srotip.userservice.producer.UserEventProducer;
import com.srotip.userservice.repository.UserRepo;
import com.srotip.userservice.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;
    private final UserEventProducer producer;

    public UserServiceImpl(UserRepo userRepo, UserEventProducer producer) {
        this.userRepo = userRepo;
        this.producer = producer;
    }

    @Override
    public UserResponseDTO createUser(UserRequestDTO user) {
        User u = new User();
        u.setName(user.getName());
        u.setEmail(user.getEmail());
        u.setPhone(user.getPhone());
        UserCreatedEvent event = new UserCreatedEvent(u.getId(), u.getEmail(), u.getName());
        producer.publicUserCreatedEvent(event);
        return mapToDTO(userRepo.save(u));
    }

    @Override
    public List<UserResponseDTO> getAllUsers() {
        return userRepo.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public UserResponseDTO getUserById(Long id) {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        return this.mapToDTO(user);
    }

    @Override
    public UserResponseDTO updateUser(Long id, UserRequestDTO request) {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());

        return mapToDTO(userRepo.save(user));
    }

    @Override
    public void deleteUser(Long id) {
        userRepo.deleteById(id);
    }

    private UserResponseDTO mapToDTO(User user) {
        UserResponseDTO dto = new UserResponseDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setPhone(user.getPhone());
        dto.setEmail(user.getEmail());
        dto.setCreatedAt(user.getCreatedAt());

        return dto;
    }

}
