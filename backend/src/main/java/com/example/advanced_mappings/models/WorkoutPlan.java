package com.example.advanced_mappings.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkoutPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "plan_id")
    private Long planId;

    @Column(name = "workout_name")
    private String workoutName;

    private String description;

    @Temporal(TemporalType.DATE)
    @Column(name = "start_date")
    private Date startDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "end_date")
    private Date endDate;

    // Member assigned to this plan
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private User memberWorkoutPlan;

    // Trainer who created this plan
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trainer_id")
    private Trainer trainer;

    @OneToMany(mappedBy = "workoutPlanWorkoutSession", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WorkoutSession> workoutSessionList = new ArrayList<>();
}
