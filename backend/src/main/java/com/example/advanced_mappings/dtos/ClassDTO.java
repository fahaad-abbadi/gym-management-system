// ClassDTO.java
package com.example.advanced_mappings.dtos;

import com.example.advanced_mappings.enums.ClassType;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClassDTO {
    private Long classId;
    private String className;
    private ClassType classType;
    private LocalDateTime scheduleTime;
    private Long durationMinutes;
    private Long maxCapacity;
    private Long trainerId;    // Still maps to Trainer.trainerId (since entity uses Trainer)

    private List<AttendanceDTO> attendances;           // ✅ should be plural (better clarity & consistency)
    private List<ClassEnrollmentDTO> enrollments;
}

