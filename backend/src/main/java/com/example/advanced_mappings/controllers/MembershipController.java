package com.example.advanced_mappings.controllers;

import com.example.advanced_mappings.dtos.MembershipDTO;
import com.example.advanced_mappings.dtos.Response;
import com.example.advanced_mappings.models.Member;
import com.example.advanced_mappings.models.Membership;
import com.example.advanced_mappings.repos.MemberRepository;
import com.example.advanced_mappings.repos.MembershipRepository;
import com.example.advanced_mappings.service.MembershipService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/memberships")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class MembershipController {

    private final MembershipService membershipService;

    // Only ADMIN can create memberships
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<Response> create(@RequestBody MembershipDTO dto) {
        return ResponseEntity.ok(membershipService.createMembership(dto));
    }

    // Only ADMIN can update memberships
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<Response> update(@PathVariable Long id, @RequestBody MembershipDTO dto) {
        return ResponseEntity.ok(membershipService.updateMembership(id, dto));
    }

    // ADMIN and MEMBER can view individual membership
    @PreAuthorize("hasAnyRole('ADMIN', 'MEMBER')")
    @GetMapping("/{id}")
    public ResponseEntity<Response> getById(@PathVariable Long id) {
        return ResponseEntity.ok(membershipService.getMembershipById(id));
    }

    // Only ADMIN can view all memberships
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<Response> getAll() {
        return ResponseEntity.ok(membershipService.getAllMemberships());
    }

    // Only ADMIN can delete
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Response> delete(@PathVariable Long id) {
        return ResponseEntity.ok(membershipService.deleteMembership(id));
    }
}