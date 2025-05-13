// src/main/java/com/example/advanced_mappings/dtos/RegisterRequest.java
package com.example.advanced_mappings.dtos;

import com.example.advanced_mappings.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    @NotBlank(message = "Name is required")
    private String userName;

    @Email(message = "Must be a valid email")
    @NotBlank(message = "Email is required")
    private String email;

    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;

    @NotBlank(message = "Phone number is required")
    private String phoneNumber;

    // initial role—they’ll default to MEMBER and then be approved for TRAINER/STAFF by admin
    private Role role;
}
