package com.example.advanced_mappings.controllers;

import com.example.advanced_mappings.dtos.Response;
import com.example.advanced_mappings.dtos.TrainerDTO;
import com.example.advanced_mappings.models.Trainer;
import com.example.advanced_mappings.repos.TrainerRepository;
import com.example.advanced_mappings.service.TrainerScheduleService;
import com.example.advanced_mappings.service.TrainerService;
import com.example.advanced_mappings.service.WorkoutPlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/trainers")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class TrainerController {

    private final TrainerService trainerService;
    private final WorkoutPlanService workoutPlanService;
    private final TrainerScheduleService trainerScheduleService;

    // Admin only
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<Response> createTrainer(@RequestBody TrainerDTO dto) {
        return ResponseEntity.ok(trainerService.createTrainer(dto));
    }

    // Admin and Trainer can view all trainers (optionally limit trainers to view self only)
    @PreAuthorize("hasAnyRole('ADMIN', 'TRAINER')")
    @GetMapping
    public ResponseEntity<Response> getAllTrainers() {
        return ResponseEntity.ok(trainerService.getAllTrainers());
    }

    // Admin or the trainer themselves
    @PreAuthorize("hasAnyRole('ADMIN', 'TRAINER')")
    @GetMapping("/{id}")
    public ResponseEntity<Response> getTrainerById(@PathVariable Long id) {
        return ResponseEntity.ok(trainerService.getTrainerById(id));
    }

    // Admin or the trainer themselves
    @PreAuthorize("hasAnyRole('ADMIN', 'TRAINER')")
    @PutMapping("/{id}")
    public ResponseEntity<Response> updateTrainer(@PathVariable Long id, @RequestBody TrainerDTO dto) {
        return ResponseEntity.ok(trainerService.updateTrainer(id, dto));
    }

    // Admin only
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Response> deleteTrainer(@PathVariable Long id) {
        return ResponseEntity.ok(trainerService.deleteTrainer(id));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'TRAINER')")
    @GetMapping("/{trainerId}/workout-plans")
    public ResponseEntity<Response> getWorkoutPlansByTrainer(@PathVariable Long trainerId) {
        return ResponseEntity.ok(workoutPlanService.getWorkoutPlansByTrainer(trainerId));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'TRAINER')")
    @GetMapping("/{trainerId}/upcoming-sessions")
    public ResponseEntity<Response> getUpcomingSessionsByTrainer(@PathVariable Long trainerId) {
        return ResponseEntity.ok(trainerScheduleService.getUpcomingSessionsByTrainer(trainerId));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'TRAINER')")
    @GetMapping("/{trainerId}/average-rating")
    public ResponseEntity<Response> getAverageRating(@PathVariable Long trainerId) {
        return ResponseEntity.ok(trainerService.getTrainerAverageRating(trainerId));
    }
}