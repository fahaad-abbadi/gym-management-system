package com.example.advanced_mappings.models;

import com.example.advanced_mappings.enums.SessionType;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "trainer_schedule")
public class TrainerSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "schedule_id")
    private Long scheduleId;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "session_date", nullable = false)
    private Date sessionDate;

    @Column(name = "session_duration", nullable = false)
    private Long sessionDuration; // minutes

    @Enumerated(EnumType.STRING)
    @Column(name = "session_type", nullable = false)
    private SessionType sessionType;

    /**
     * The trainer running the session.
     * Points back to User with role = TRAINER.
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "trainer_id", nullable = false)
    private User trainer;

    /**
     * The member attending the session.
     * Points back to User with role = MEMBER.
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "member_id", nullable = false)
    private User member;
}
