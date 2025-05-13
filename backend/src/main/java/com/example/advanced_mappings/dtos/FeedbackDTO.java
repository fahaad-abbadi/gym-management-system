// FeedbackDTO.java
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
public class FeedbackDTO {
    private Long feedbackId;
    private String feedbackText;
    private Long rating;
    private Date feedbackDate;

    // Only for sending to frontend
    private String memberName;   // From member.getUserName()
    private String trainerName;  // From trainer.getUserName()

    // Optional: Keep IDs only for creation & update (not for response)
    private Long userId;
    private Long trainerId;
}

