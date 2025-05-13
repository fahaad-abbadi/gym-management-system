// AttendanceDTO.java
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
public class AttendanceDTO {
    private Long attendanceId;
    private Date attendanceDate;
    private Boolean attended;
    private Long userId;
    private Long classId;
}

