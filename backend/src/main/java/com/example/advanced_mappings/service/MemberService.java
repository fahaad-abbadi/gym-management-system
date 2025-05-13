package com.example.advanced_mappings.service;

import com.example.advanced_mappings.dtos.MemberDTO;
import com.example.advanced_mappings.dtos.Response;
import com.example.advanced_mappings.models.Member;

public interface MemberService {
    Response createMember(MemberDTO memberDTO);
    Response getAllMembers();
    Response getMemberById(Long id);
    Response updateMember(Long id, MemberDTO memberDTO);
    Response deleteMember(Long id);
    Response addMembershipToMember(Long memberId, Long membershipId);
    Response addWorkoutPlanToMember(Long memberId, Long planId);
    Response addWorkoutSessionToMember(Long memberId, Long sessionId);
    Response addAttendanceToMember(Long memberId, Long attendanceId);
    Response addClassEnrollmentToMember(Long memberId, Long enrollmentId);
    Response addDietPlanToMember(Long memberId, Long dietPlanId);
    Response addTrainerScheduleToMember(Long memberId, Long trainerScheduleId);
}
