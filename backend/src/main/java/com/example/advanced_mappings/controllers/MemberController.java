package com.example.advanced_mappings.controllers;

import com.example.advanced_mappings.dtos.MemberDTO;
import com.example.advanced_mappings.dtos.Response;
import com.example.advanced_mappings.models.*;
import com.example.advanced_mappings.repos.*;
import com.example.advanced_mappings.service.MemberService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/members")
@CrossOrigin(origins = "http://localhost:3000")
@AllArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<Response> getMembers() {
        return ResponseEntity.ok(memberService.getAllMembers());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<Response> createMember(@RequestBody MemberDTO dto) {
        return ResponseEntity.ok(memberService.createMember(dto));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{memberId}/membership/{membershipId}")
    public ResponseEntity<Response> addMembership(@PathVariable Long memberId, @PathVariable Long membershipId) {
        return ResponseEntity.ok(memberService.addMembershipToMember(memberId, membershipId));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{memberId}/workoutplan/{workoutPlanId}")
    public ResponseEntity<Response> addWorkoutPlan(@PathVariable Long memberId, @PathVariable Long workoutPlanId) {
        return ResponseEntity.ok(memberService.addWorkoutPlanToMember(memberId, workoutPlanId));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{memberId}/workoutSession/{workoutSessionId}")
    public ResponseEntity<Response> addWorkoutSession(@PathVariable Long memberId, @PathVariable Long workoutSessionId) {
        return ResponseEntity.ok(memberService.addWorkoutSessionToMember(memberId, workoutSessionId));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{memberId}/attendance/{attendanceId}")
    public ResponseEntity<Response> addAttendance(@PathVariable Long memberId, @PathVariable Long attendanceId) {
        return ResponseEntity.ok(memberService.addAttendanceToMember(memberId, attendanceId));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{memberId}/classEnrollment/{classEnrollmentId}")
    public ResponseEntity<Response> addClassEnrollment(@PathVariable Long memberId, @PathVariable Long classEnrollmentId) {
        return ResponseEntity.ok(memberService.addClassEnrollmentToMember(memberId, classEnrollmentId));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{memberId}/diet_plan/{dietPlanId}")
    public ResponseEntity<Response> addDietPlan(@PathVariable Long memberId, @PathVariable Long dietPlanId) {
        return ResponseEntity.ok(memberService.addDietPlanToMember(memberId, dietPlanId));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{memberId}/trainer_schedule/{trainerScheduleId}")
    public ResponseEntity<Response> addTrainerSchedule(@PathVariable Long memberId, @PathVariable Long trainerScheduleId) {
        return ResponseEntity.ok(memberService.addTrainerScheduleToMember(memberId, trainerScheduleId));
    }
}