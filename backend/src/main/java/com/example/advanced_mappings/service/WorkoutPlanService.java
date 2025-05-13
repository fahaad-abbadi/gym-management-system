package com.example.advanced_mappings.service;

import com.example.advanced_mappings.dtos.Response;
import com.example.advanced_mappings.dtos.WorkoutPlanDTO;

public interface WorkoutPlanService {
    Response createWorkoutPlan(WorkoutPlanDTO workoutPlanDTO);
    Response getAllWorkoutPlans();
    Response getWorkoutPlanById(Long id);
    Response updateWorkoutPlan(Long id, WorkoutPlanDTO workoutPlanDTO);
    Response deleteWorkoutPlan(Long id);
    Response getWorkoutPlansByTrainer(Long trainerId);
}
