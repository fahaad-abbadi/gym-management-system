package com.example.advanced_mappings.controllers;

import com.example.advanced_mappings.dtos.Response;
import com.example.advanced_mappings.dtos.StaffDTO;
import com.example.advanced_mappings.models.Staff;
import com.example.advanced_mappings.repos.StaffRepository;
import com.example.advanced_mappings.service.StaffService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/staff")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class StaffController {

    private final StaffService staffService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<Response> create(@RequestBody StaffDTO dto) {
        return ResponseEntity.ok(staffService.createStaff(dto));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @PutMapping("/{id}")
    public ResponseEntity<Response> update(@PathVariable Long id, @RequestBody StaffDTO dto) {
        return ResponseEntity.ok(staffService.updateStaff(id, dto));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @GetMapping("/{id}")
    public ResponseEntity<Response> getById(@PathVariable Long id) {
        return ResponseEntity.ok(staffService.getStaffById(id));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @GetMapping
    public ResponseEntity<Response> getAll() {
        return ResponseEntity.ok(staffService.getAllStaff());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Response> delete(@PathVariable Long id) {
        return ResponseEntity.ok(staffService.deleteStaff(id));
    }
}