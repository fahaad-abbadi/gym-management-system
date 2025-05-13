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
public class WorkoutSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "session_id")
    private Long sessionId;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "session_date", nullable = false)
    private Date sessionDate;

    @Column(name = "duration_minutes")
    private Long durationMinutes;

    @Column(name = "calories_burned")
    private Long caloriesBurned;

    private String feedback;

    /**
     * The member who completed this workout session.
     * Role must be MEMBER.
     */
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", referencedColumnName = "id")
    private User memberWorkoutSession;

    /**
     * The workout plan this session belongs to.
     */
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workout_id", referencedColumnName = "plan_id")
    private WorkoutPlan workoutPlanWorkoutSession;
}
