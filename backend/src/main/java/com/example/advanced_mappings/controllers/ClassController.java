package com.example.advanced_mappings.controllers;

import com.example.advanced_mappings.dtos.ClassDTO;
import com.example.advanced_mappings.dtos.Response;
import com.example.advanced_mappings.models.Class;
import com.example.advanced_mappings.repos.ClassRepository;
import com.example.advanced_mappings.service.ClassService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/classes")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ClassController {

    private final ClassService classService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<Response> create(@RequestBody ClassDTO dto) {
        return ResponseEntity.ok(classService.createClass(dto));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<Response> update(@PathVariable Long id, @RequestBody ClassDTO dto) {
        return ResponseEntity.ok(classService.updateClass(id, dto));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MEMBER', 'TRAINER')")
    @GetMapping("/{id}")
    public ResponseEntity<Response> getById(@PathVariable Long id) {
        return ResponseEntity.ok(classService.getClassById(id));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MEMBER', 'TRAINER')")
    @GetMapping
    public ResponseEntity<Response> getAll() {
        return ResponseEntity.ok(classService.getAllClasses());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Response> delete(@PathVariable Long id) {
        return ResponseEntity.ok(classService.deleteClass(id));
    }
}
