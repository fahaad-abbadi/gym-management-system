package com.example.advanced_mappings.service;

import com.example.advanced_mappings.dtos.Response;
import com.example.advanced_mappings.dtos.TrainerDTO;
import com.example.advanced_mappings.models.Trainer;

import java.util.List;

public interface TrainerService {
    Response createTrainer(TrainerDTO trainerDTO);
    Response getAllTrainers();
    Response getTrainerById(Long id);
    Response updateTrainer(Long id, TrainerDTO trainerDTO);
    Response deleteTrainer(Long id);
    Response getTrainerAverageRating(Long trainerId);
}
