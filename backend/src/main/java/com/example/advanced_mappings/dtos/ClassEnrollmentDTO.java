// ClassEnrollmentDTO.java
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
public class ClassEnrollmentDTO {
    private Long enrollmentId;
    private Date enrollmentDate;
    private Long classId;
    private Long userId;    // ✅ Correct: refers to User.id instead of memberId
}

