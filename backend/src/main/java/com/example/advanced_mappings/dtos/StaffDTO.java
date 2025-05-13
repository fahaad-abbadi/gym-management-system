package com.example.advanced_mappings.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StaffDTO {
    private Long staffId;
    private Long userId;                  // ✅ Linked User ID
    private String userName;              // From User
    private String email;                 // From User
    private String phoneNumber;           // From User

    // Staff-specific fields:
    private String positionTitle;
    private String hireDate;              // Should ideally be LocalDate (ISO string)
    private Double salary;
}

