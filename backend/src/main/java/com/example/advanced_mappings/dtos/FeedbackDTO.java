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
    private Long userId;       // ✅ Member User ID (who gave feedback)
    private Long trainerId;    // ✅ Trainer User ID (who received feedback)
}

