package com.example.advanced_mappings.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberDTO {
    private Long memberId;
    private Long userId;                  // ✅ Link to the User (main account)
    private String firstName;             // From User
    private String lastName;              // From User
    private String email;                 // From User
    private String phoneNumber;           // From User
    private String dateOfBirth;           // From Member profile
    private String gender;                // From Member profile
    private String joinDate;              // From Member profile
    private String address;
    private String city;
    private String state;
    private String postalCode;
    private String emergencyContactName;
    private String emergencyContactPhone;
}

