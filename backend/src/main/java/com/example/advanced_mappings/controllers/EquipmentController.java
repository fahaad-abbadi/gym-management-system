package com.example.advanced_mappings.controllers;

import com.example.advanced_mappings.dtos.EquipmentDTO;
import com.example.advanced_mappings.dtos.Response;
import com.example.advanced_mappings.models.Equipment;
import com.example.advanced_mappings.repos.EquipmentRepository;
import com.example.advanced_mappings.service.EquipmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/equipment")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class EquipmentController {

    private final EquipmentService equipmentService;

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @PostMapping
    public ResponseEntity<Response> create(@RequestBody EquipmentDTO dto) {
        return ResponseEntity.ok(equipmentService.createEquipment(dto));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @PutMapping("/{id}")
    public ResponseEntity<Response> update(@PathVariable Long id, @RequestBody EquipmentDTO dto) {
        return ResponseEntity.ok(equipmentService.updateEquipment(id, dto));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @GetMapping("/{id}")
    public ResponseEntity<Response> getById(@PathVariable Long id) {
        return ResponseEntity.ok(equipmentService.getEquipmentById(id));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'STAFF')")
    @GetMapping
    public ResponseEntity<Response> getAll() {
        return ResponseEntity.ok(equipmentService.getAllEquipments());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Response> delete(@PathVariable Long id) {
        return ResponseEntity.ok(equipmentService.deleteEquipment(id));
    }
}
