package com.example.advanced_mappings.service;

import com.example.advanced_mappings.dtos.Response;
import com.example.advanced_mappings.dtos.WorkoutSessionDTO;

public interface WorkoutSessionService {

    Response createWorkoutSession(WorkoutSessionDTO dto);

    Response getAllWorkoutSessions();

    Response getWorkoutSessionById(Long id);

    Response deleteWorkoutSession(Long id);

    Response updateWorkoutSession(Long id, WorkoutSessionDTO dto);
}
