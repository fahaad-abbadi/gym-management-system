package com.example.advanced_mappings.service;

import com.example.advanced_mappings.dtos.LoginRequest;
import com.example.advanced_mappings.dtos.RegisterRequest;
import com.example.advanced_mappings.dtos.Response;
import com.example.advanced_mappings.dtos.UserDTO;
import com.example.advanced_mappings.enums.Role;
import com.example.advanced_mappings.models.User;

public interface UserService {
    // Auth-related:
    Response registerUser(RegisterRequest registerRequest);   // ✅ Takes RegisterRequest
    Response loginUser(LoginRequest loginRequest);            // ✅ Takes LoginRequest

    // User management:
    Response getAllUsers();
    Response getUserById(Long id);                            // ✅ Returns wrapped Response with UserDTO
    Response updateUser(Long id, UserDTO userDTO);
    Response deleteUser(Long id);

    // Role and approval management:
    Response getPendingTrainers();
    Response approveTrainer(Long userId);
    Response updateRole(Long userId, Role newRole);

    // Session-related:
    Response getClientsForTrainer(Long trainerId);

    // Current user:
    User getCurrentLoggedInUser();
}
