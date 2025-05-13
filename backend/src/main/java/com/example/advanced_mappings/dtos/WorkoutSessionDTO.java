// WorkoutSessionDTO.java
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
public class WorkoutSessionDTO {
    private Long sessionId;
    private Date sessionDate;
    private Long durationMinutes;
    private Long caloriesBurned;
    private String feedback;
    private Long userId;     // ✅ Member User ID (refactored from memberId)
    private Long planId;     // WorkoutPlan ID
}

