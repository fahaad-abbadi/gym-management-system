package com.example.advanced_mappings.controllers;

import com.example.advanced_mappings.dtos.Response;
import com.example.advanced_mappings.dtos.TrainerScheduleDTO;
import com.example.advanced_mappings.models.TrainerSchedule;
import com.example.advanced_mappings.repos.TrainerScheduleRepository;
import com.example.advanced_mappings.service.TrainerScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/trainer-schedule")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class TrainerScheduleController {

    private final TrainerScheduleService trainerScheduleService;

    @PreAuthorize("hasAnyRole('ADMIN', 'TRAINER')")
    @PostMapping
    public ResponseEntity<Response> create(@RequestBody TrainerScheduleDTO dto) {
        return ResponseEntity.ok(trainerScheduleService.createTrainerSchedule(dto));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'TRAINER')")
    @PutMapping("/{id}")
    public ResponseEntity<Response> update(@PathVariable Long id, @RequestBody TrainerScheduleDTO dto) {
        return ResponseEntity.ok(trainerScheduleService.updateTrainerSchedule(id, dto));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'TRAINER', 'MEMBER')")
    @GetMapping("/{id}")
    public ResponseEntity<Response> getById(@PathVariable Long id) {
        return ResponseEntity.ok(trainerScheduleService.getTrainerScheduleById(id));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'TRAINER')")
    @GetMapping
    public ResponseEntity<Response> getAll() {
        return ResponseEntity.ok(trainerScheduleService.getAllTrainerSchedules());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Response> delete(@PathVariable Long id) {
        return ResponseEntity.ok(trainerScheduleService.deleteTrainerSchedule(id));
    }
}