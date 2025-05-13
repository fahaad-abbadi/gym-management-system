package com.example.advanced_mappings.repos;

import com.example.advanced_mappings.models.Trainer;
import com.example.advanced_mappings.models.WorkoutPlan;
import com.example.advanced_mappings.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WorkoutPlanRepository extends JpaRepository<WorkoutPlan, Long> {
    List<WorkoutPlan> findByMemberWorkoutPlan(User member);
    List<WorkoutPlan> findByTrainer(Trainer trainer);

    @Query("SELECT wp FROM WorkoutPlan wp WHERE wp.trainer.user.id = :trainerUserId")
    List<WorkoutPlan> findByTrainerUserId(@Param("trainerUserId") Long trainerUserId);
}

