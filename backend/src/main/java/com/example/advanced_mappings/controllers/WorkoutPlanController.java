package com.example.advanced_mappings.controllers;

import com.example.advanced_mappings.dtos.Response;
import com.example.advanced_mappings.dtos.WorkoutPlanDTO;
import com.example.advanced_mappings.models.WorkoutPlan;
import com.example.advanced_mappings.repos.WorkoutPlanRepository;
import com.example.advanced_mappings.service.WorkoutPlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/workout-plans")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class WorkoutPlanController {

    private final WorkoutPlanService workoutPlanService;

    // ADMIN or TRAINER
    @PreAuthorize("hasAnyRole('ADMIN', 'TRAINER')")
    @PostMapping
    public ResponseEntity<Response> createPlan(@RequestBody WorkoutPlanDTO dto) {
        return ResponseEntity.ok(workoutPlanService.createWorkoutPlan(dto));
    }

    // ADMIN or TRAINER
    @PreAuthorize("hasAnyRole('ADMIN', 'TRAINER')")
    @PutMapping("/{id}")
    public ResponseEntity<Response> updatePlan(@PathVariable Long id, @RequestBody WorkoutPlanDTO dto) {
        return ResponseEntity.ok(workoutPlanService.updateWorkoutPlan(id, dto));
    }

    // ADMIN or MEMBER can read
    @PreAuthorize("hasAnyRole('ADMIN', 'TRAINER', 'MEMBER')")
    @GetMapping("/{id}")
    public ResponseEntity<Response> getById(@PathVariable Long id) {
        return ResponseEntity.ok(workoutPlanService.getWorkoutPlanById(id));
    }

    // ADMIN and TRAINER can view all (optionally restrict TRAINER view to own)
    @PreAuthorize("hasAnyRole('ADMIN', 'TRAINER')")
    @GetMapping
    public ResponseEntity<Response> getAll() {
        return ResponseEntity.ok(workoutPlanService.getAllWorkoutPlans());
    }

    // ADMIN only
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Response> deletePlan(@PathVariable Long id) {
        return ResponseEntity.ok(workoutPlanService.deleteWorkoutPlan(id));
    }
}
