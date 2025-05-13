// WorkoutPlanDTO.java
package com.example.advanced_mappings.dtos;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkoutPlanDTO {
    private Long planId;
    private String workoutName;
    private String description;
    private Date startDate;
    private Date endDate;
    private Long userId;        // ✅ Member (User ID)
    private Long trainerId;     // ✅ Trainer User ID
    private List<WorkoutSessionDTO> sessions;
}

