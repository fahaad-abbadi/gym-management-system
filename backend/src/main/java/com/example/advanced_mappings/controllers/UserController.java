package com.example.advanced_mappings.controllers;

import com.example.advanced_mappings.dtos.Response;
import com.example.advanced_mappings.dtos.UserDTO;
import com.example.advanced_mappings.enums.Role;
import com.example.advanced_mappings.models.User;
import com.example.advanced_mappings.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class UserController {

    private final UserService userService;
    private final ModelMapper modelMapper;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<Response> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'STAFF')")
    @GetMapping("/{id}")
    public ResponseEntity<Response> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @PutMapping("/{id}")
    public ResponseEntity<Response> updateUser(@PathVariable Long id, @RequestBody @Valid UserDTO userDTO) {
        return ResponseEntity.ok(userService.updateUser(id, userDTO));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Response> deleteUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.deleteUser(id));
    }

    @GetMapping("/pending-trainers")
    public ResponseEntity<Response> getPendingTrainers() {
        return ResponseEntity.ok(userService.getPendingTrainers());
    }

    @PutMapping("/approve-trainer/{id}")
    public ResponseEntity<Response> approveTrainer(@PathVariable Long id) {
        return ResponseEntity.ok(userService.approveTrainer(id));
    }

    @PutMapping("/update-role/{userId}")
    public ResponseEntity<Response> updateUserRole(
            @PathVariable Long userId,
            @RequestParam Role newRole) {
        return ResponseEntity.ok(userService.updateRole(userId, newRole));
    }

    @GetMapping("/current")
    public ResponseEntity<Response> getCurrentUser() {
        // ✅ Correct usage: get current user from service, map to UserDTO using modelMapper
        User currentUser = userService.getCurrentLoggedInUser();
        UserDTO dto = modelMapper.map(currentUser, UserDTO.class);

        return ResponseEntity.ok(
                Response.builder()
                        .status(200)
                        .message("Current user retrieved successfully")
                        .user(dto)
                        .build()
        );
    }
}
