package com.example.advanced_mappings.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "feedback_id")
    private Long feedbackId;

    @Column(name = "feedback_text", nullable = false)
    private String feedbackText;

    @Column(nullable = false)
    private Long rating;

    @Temporal(TemporalType.DATE)
    @Column(name = "feedback_date", nullable = false)
    private Date feedbackDate;

    /**
     * Member who gave the feedback.
     */
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "member_id", nullable = false)
    private User member;

    /**
     * Trainer who received the feedback.
     */
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "trainer_id", nullable = false)
    private User trainer;
}

