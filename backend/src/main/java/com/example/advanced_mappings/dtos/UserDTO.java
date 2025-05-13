// UserDTO.java
package com.example.advanced_mappings.dtos;

import com.example.advanced_mappings.enums.Role;
import com.example.advanced_mappings.enums.ApprovalStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDTO {

    private Long id;

    private String userName;                 // Still keep your domain-specific field name

    private String email;

    @JsonIgnore                               // Always ignore password in responses (best practice)
    private String password;

    private String phoneNumber;

    private Role role;                        // Your gym-specific Role

    private ApprovalStatus approvalStatus;    // Your gym-specific ApprovalStatus

    private Long assignedTrainerId;           // For member-trainer link
}

