package com.example.advanced_mappings.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TrainerDTO {
    private Long trainerId;
    private Long userId;                  // ✅ Linked to User.id
    private String userName;              // From User.userName
    private String email;                 // From User.email
    private String phoneNumber;           // From User.phoneNumber

    // Trainer-specific fields:
    private String speciality;
    private Date hireDate;
}
