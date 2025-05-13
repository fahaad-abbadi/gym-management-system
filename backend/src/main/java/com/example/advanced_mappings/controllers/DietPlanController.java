package com.example.advanced_mappings.controllers;

import com.example.advanced_mappings.dtos.DietPlanDTO;
import com.example.advanced_mappings.dtos.Response;
import com.example.advanced_mappings.models.DietPlan;
import com.example.advanced_mappings.repos.DietPlanRepository;
import com.example.advanced_mappings.service.DietPlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/diet-plans")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class DietPlanController {

    private final DietPlanService dietPlanService;

    @PreAuthorize("hasAnyRole('ADMIN', 'TRAINER')")
    @PostMapping
    public ResponseEntity<Response> create(@RequestBody DietPlanDTO dto) {
        return ResponseEntity.ok(dietPlanService.createDietPlan(dto));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'TRAINER')")
    @PutMapping("/{id}")
    public ResponseEntity<Response> update(@PathVariable Long id, @RequestBody DietPlanDTO dto) {
        return ResponseEntity.ok(dietPlanService.updateDietPlan(id, dto));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'TRAINER', 'MEMBER')")
    @GetMapping("/{id}")
    public ResponseEntity<Response> getById(@PathVariable Long id) {
        return ResponseEntity.ok(dietPlanService.getDietPlanById(id));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'TRAINER')")
    @GetMapping
    public ResponseEntity<Response> getAll() {
        return ResponseEntity.ok(dietPlanService.getAllDietPlans());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Response> delete(@PathVariable Long id) {
        return ResponseEntity.ok(dietPlanService.deleteDietPlan(id));
    }
}