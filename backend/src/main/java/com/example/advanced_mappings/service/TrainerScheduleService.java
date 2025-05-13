package com.example.advanced_mappings.service;

import com.example.advanced_mappings.dtos.Response;
import com.example.advanced_mappings.dtos.TrainerScheduleDTO;

public interface TrainerScheduleService {
    Response createTrainerSchedule(TrainerScheduleDTO trainerScheduleDTO);
    Response getAllTrainerSchedules();
    Response getTrainerScheduleById(Long id);
    Response updateTrainerSchedule(Long id, TrainerScheduleDTO trainerScheduleDTO);
    Response deleteTrainerSchedule(Long id);
    Response getUpcomingSessionsByTrainer(Long trainerId);
}
