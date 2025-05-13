package com.example.advanced_mappings.controllers;

import com.example.advanced_mappings.dtos.AttendanceDTO;
import com.example.advanced_mappings.dtos.Response;
import com.example.advanced_mappings.models.Attendance;
import com.example.advanced_mappings.repos.AttendanceRepository;
import com.example.advanced_mappings.service.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/attendances")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AttendanceController {

    private final AttendanceService attendanceService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<Response> create(@RequestBody AttendanceDTO dto) {
        return ResponseEntity.ok(attendanceService.createAttendance(dto));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<Response> update(@PathVariable Long id, @RequestBody AttendanceDTO dto) {
        return ResponseEntity.ok(attendanceService.updateAttendance(id, dto));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MEMBER')")
    @GetMapping("/{id}")
    public ResponseEntity<Response> getById(@PathVariable Long id) {
        return ResponseEntity.ok(attendanceService.getAttendanceById(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<Response> getAll() {
        return ResponseEntity.ok(attendanceService.getAllAttendances());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Response> delete(@PathVariable Long id) {
        return ResponseEntity.ok(attendanceService.deleteAttendance(id));
    }
}

