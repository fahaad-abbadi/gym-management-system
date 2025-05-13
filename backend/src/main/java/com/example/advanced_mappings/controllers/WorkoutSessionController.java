package com.example.advanced_mappings.controllers;

import com.example.advanced_mappings.dtos.Response;
import com.example.advanced_mappings.dtos.WorkoutSessionDTO;
import com.example.advanced_mappings.models.WorkoutSession;
import com.example.advanced_mappings.repos.WorkoutSessionRepository;
import com.example.advanced_mappings.service.WorkoutSessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/workout-sessions")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class WorkoutSessionController {

    private final WorkoutSessionService workoutSessionService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<Response> create(@RequestBody WorkoutSessionDTO dto) {
        return ResponseEntity.ok(workoutSessionService.createWorkoutSession(dto));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<Response> update(@PathVariable Long id, @RequestBody WorkoutSessionDTO dto) {
        return ResponseEntity.ok(workoutSessionService.updateWorkoutSession(id, dto));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MEMBER')")
    @GetMapping("/{id}")
    public ResponseEntity<Response> getById(@PathVariable Long id) {
        return ResponseEntity.ok(workoutSessionService.getWorkoutSessionById(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<Response> getAll() {
        return ResponseEntity.ok(workoutSessionService.getAllWorkoutSessions());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Response> delete(@PathVariable Long id) {
        return ResponseEntity.ok(workoutSessionService.deleteWorkoutSession(id));
    }
}

