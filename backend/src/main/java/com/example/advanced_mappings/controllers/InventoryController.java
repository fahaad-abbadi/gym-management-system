package com.example.advanced_mappings.controllers;

import com.example.advanced_mappings.dtos.InventoryDTO;
import com.example.advanced_mappings.dtos.Response;
import com.example.advanced_mappings.models.Inventory;
import com.example.advanced_mappings.repos.InventoryRepository;
import com.example.advanced_mappings.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class InventoryController {

    private final InventoryService inventoryService;

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @PostMapping
    public ResponseEntity<Response> create(@RequestBody InventoryDTO dto) {
        return ResponseEntity.ok(inventoryService.createInventory(dto));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @PutMapping("/{id}")
    public ResponseEntity<Response> update(@PathVariable Long id, @RequestBody InventoryDTO dto) {
        return ResponseEntity.ok(inventoryService.updateInventory(id, dto));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @GetMapping("/{id}")
    public ResponseEntity<Response> getById(@PathVariable Long id) {
        return ResponseEntity.ok(inventoryService.getInventoryById(id));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'STAFF')")
    @GetMapping
    public ResponseEntity<Response> getAll() {
        return ResponseEntity.ok(inventoryService.getAllInventories());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Response> delete(@PathVariable Long id) {
        return ResponseEntity.ok(inventoryService.deleteInventory(id));
    }
}