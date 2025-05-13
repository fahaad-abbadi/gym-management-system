// DietPlanDTO.java
package com.example.advanced_mappings.dtos;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DietPlanDTO {
    private Long dietPlanId;
    private String planName;
    private String description;
    private Date startDate;
    private Date endDate;
    private Long userId;       // ✅ Refers to User.id (Member role)
    private Long trainerId;    // Trainer entity still exists as per your model
}

