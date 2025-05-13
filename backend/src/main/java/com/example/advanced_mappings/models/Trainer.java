package com.example.advanced_mappings.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Trainer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "trainer_id")
    private Long trainerId;

    /**
     * Linked user account with shared credentials.
     * This user must have role = TRAINER.
     */
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    /**
     * Trainer-specific metadata.
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "hire_date")
    private Date hireDate;

    private String speciality;

    /**
     * All workout plans created by this trainer.
     */
    @OneToMany(mappedBy = "trainer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WorkoutPlan> workoutPlanList = new ArrayList<>();

    /**
     * All classes taught by this trainer.
     */
    @OneToMany(mappedBy = "trainerClass", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Class> classList = new ArrayList<>();

    /**
     * All diet plans authored by this trainer.
     */
    @OneToMany(mappedBy = "trainerDietPlan", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DietPlan> trainerDietPlanList = new ArrayList<>();

    // 🔁 Bidirectional sync helpers
    public void addWorkoutPlan(WorkoutPlan plan) {
        workoutPlanList.add(plan);
        plan.setTrainer(this);
    }

    public void addClass(Class gymClass) {
        classList.add(gymClass);
        gymClass.setTrainerClass(this);
    }

    public void addDietPlan(DietPlan dietPlan) {
        trainerDietPlanList.add(dietPlan);
        dietPlan.setTrainerDietPlan(this);
    }
}
