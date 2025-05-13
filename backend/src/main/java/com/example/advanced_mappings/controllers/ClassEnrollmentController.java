package com.example.advanced_mappings.controllers;

import com.example.advanced_mappings.dtos.ClassEnrollmentDTO;
import com.example.advanced_mappings.dtos.Response;
import com.example.advanced_mappings.models.ClassEnrollment;
import com.example.advanced_mappings.repos.ClassEnrollmentRepository;
import com.example.advanced_mappings.repos.ClassRepository;
import com.example.advanced_mappings.service.ClassEnrollmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/class-enrollments")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ClassEnrollmentController {

    private final ClassEnrollmentService classEnrollmentService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<Response> create(@RequestBody ClassEnrollmentDTO dto) {
        return ResponseEntity.ok(classEnrollmentService.createClassEnrollment(dto));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<Response> update(@PathVariable Long id, @RequestBody ClassEnrollmentDTO dto) {
        return ResponseEntity.ok(classEnrollmentService.updateClassEnrollment(id, dto));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MEMBER')")
    @GetMapping("/{id}")
    public ResponseEntity<Response> getById(@PathVariable Long id) {
        return ResponseEntity.ok(classEnrollmentService.getClassEnrollmentById(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<Response> getAll() {
        return ResponseEntity.ok(classEnrollmentService.getAllClassEnrollments());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Response> delete(@PathVariable Long id) {
        return ResponseEntity.ok(classEnrollmentService.deleteClassEnrollment(id));
    }
}

