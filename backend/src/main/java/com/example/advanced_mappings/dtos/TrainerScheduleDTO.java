// TrainerScheduleDTO.java
package com.example.advanced_mappings.dtos;

import com.example.advanced_mappings.enums.SessionType;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrainerScheduleDTO {
    private Long scheduleId;
    private Date sessionDate;
    private Long sessionDuration;
    private SessionType sessionType;
    private Long trainerUserId;   // ✅ Refers to User.id with TRAINER role
    private Long memberUserId;    // ✅ Refers to User.id with MEMBER role
}

