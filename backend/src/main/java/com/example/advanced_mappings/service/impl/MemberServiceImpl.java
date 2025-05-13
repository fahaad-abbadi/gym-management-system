package com.example.advanced_mappings.service.impl;

import com.example.advanced_mappings.dtos.MemberDTO;
import com.example.advanced_mappings.dtos.Response;
import com.example.advanced_mappings.exceptions.NotFoundException;
import com.example.advanced_mappings.models.*;
import com.example.advanced_mappings.repos.*;
import com.example.advanced_mappings.security.CurrentUserService;
import com.example.advanced_mappings.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final UserRepository userRepository;
    private final MembershipRepository membershipRepository;
    private final WorkoutPlanRepository workoutPlanRepository;
    private final WorkoutSessionRepository workoutSessionRepository;
    private final AttendanceRepository attendanceRepository;
    private final ClassEnrollmentRepository classEnrollmentRepository;
    private final DietPlanRepository dietPlanRepository;
    private final TrainerScheduleRepository trainerScheduleRepository;
    private final ModelMapper modelMapper;
    private final CurrentUserService currentUserService;

    @Override
    public Response createMember(MemberDTO dto) {
        Member member = modelMapper.map(dto, Member.class);
        memberRepository.save(member);
        return Response.builder().status(200).message("Member created").build();
    }

    @Override
    public Response getAllMembers() {
        List<Member> members = memberRepository.findAll(Sort.by(Sort.Direction.DESC, "memberId"));
        List<MemberDTO> dtoList = modelMapper.map(members, new TypeToken<List<MemberDTO>>() {}.getType());
        return Response.builder().status(200).message("success").members(dtoList).build();
    }

    @Override
    public Response getMemberById(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Member not found"));
        enforceOwnershipOrAdmin(member.getUser().getEmail());
        MemberDTO dto = modelMapper.map(member, MemberDTO.class);
        return Response.builder().status(200).message("success").member(dto).build();
    }

    @Override
    public Response updateMember(Long id, MemberDTO dto) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Member not found"));
        enforceOwnershipOrAdmin(member.getUser().getEmail());
        modelMapper.map(dto, member);
        memberRepository.save(member);
        return Response.builder().status(200).message("Member updated").build();
    }

    @Override
    public Response deleteMember(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Member not found"));
        memberRepository.delete(member);
        return Response.builder().status(200).message("Member deleted").build();
    }

    @Override
    public Response addMembershipToMember(Long memberId, Long membershipId) {
        User user = getUserFromMemberId(memberId);
        Membership membership = membershipRepository.findById(membershipId)
                .orElseThrow(() -> new NotFoundException("Membership not found"));
        user.addMembership(membership);
        userRepository.save(user);
        return Response.builder().status(200).message("Membership linked").build();
    }

    @Override
    public Response addWorkoutPlanToMember(Long memberId, Long planId) {
        User user = getUserFromMemberId(memberId);
        WorkoutPlan plan = workoutPlanRepository.findById(planId)
                .orElseThrow(() -> new NotFoundException("Workout plan not found"));
        user.addWorkoutPlan(plan);
        userRepository.save(user);
        return Response.builder().status(200).message("Workout plan linked").build();
    }

    @Override
    public Response addWorkoutSessionToMember(Long memberId, Long sessionId) {
        User user = getUserFromMemberId(memberId);
        WorkoutSession session = workoutSessionRepository.findById(sessionId)
                .orElseThrow(() -> new NotFoundException("Workout session not found"));
        user.addWorkoutSession(session);
        userRepository.save(user);
        return Response.builder().status(200).message("Workout session linked").build();
    }

    @Override
    public Response addAttendanceToMember(Long memberId, Long attendanceId) {
        User user = getUserFromMemberId(memberId);
        Attendance attendance = attendanceRepository.findById(attendanceId)
                .orElseThrow(() -> new NotFoundException("Attendance not found"));
        user.addAttendance(attendance);
        userRepository.save(user);
        return Response.builder().status(200).message("Attendance linked").build();
    }

    @Override
    public Response addClassEnrollmentToMember(Long memberId, Long enrollmentId) {
        User user = getUserFromMemberId(memberId);
        ClassEnrollment enrollment = classEnrollmentRepository.findById(enrollmentId)
                .orElseThrow(() -> new NotFoundException("Enrollment not found"));
        user.addClassEnrollment(enrollment);
        userRepository.save(user);
        return Response.builder().status(200).message("Class enrollment linked").build();
    }

    @Override
    public Response addDietPlanToMember(Long memberId, Long dietPlanId) {
        User user = getUserFromMemberId(memberId);
        DietPlan diet = dietPlanRepository.findById(dietPlanId)
                .orElseThrow(() -> new NotFoundException("Diet plan not found"));
        user.addDietPlan(diet);
        userRepository.save(user);
        return Response.builder().status(200).message("Diet plan linked").build();
    }

    @Override
    public Response addTrainerScheduleToMember(Long memberId, Long trainerScheduleId) {
        User user = getUserFromMemberId(memberId);
        TrainerSchedule schedule = trainerScheduleRepository.findById(trainerScheduleId)
                .orElseThrow(() -> new NotFoundException("Trainer schedule not found"));
        user.addAttendedSchedule(schedule);
        userRepository.save(user);
        return Response.builder().status(200).message("Trainer schedule linked").build();
    }

    // ✅ Helper to get User from MemberId
    private User getUserFromMemberId(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundException("Member not found"));
        return member.getUser();
    }

    // 🔐 Shared logic to restrict self-access unless ADMIN
    private void enforceOwnershipOrAdmin(String targetEmail) {
        String currentUserEmail = currentUserService.getCurrentUsername();
        boolean isAdmin = SecurityContextHolder.getContext().getAuthentication().getAuthorities()
                .stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));
        if (!isAdmin && !currentUserEmail.equalsIgnoreCase(targetEmail)) {
            throw new SecurityException("You are not authorized to access this member's data.");
        }
    }
}

