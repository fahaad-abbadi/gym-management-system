package com.example.advanced_mappings.service.impl;

import com.example.advanced_mappings.dtos.Response;
import com.example.advanced_mappings.dtos.WorkoutPlanDTO;
import com.example.advanced_mappings.exceptions.NotFoundException;
import com.example.advanced_mappings.models.Trainer;
import com.example.advanced_mappings.models.User;
import com.example.advanced_mappings.models.WorkoutPlan;
import com.example.advanced_mappings.repos.TrainerRepository;
import com.example.advanced_mappings.repos.UserRepository;
import com.example.advanced_mappings.repos.WorkoutPlanRepository;
import com.example.advanced_mappings.security.CurrentUserService;
import com.example.advanced_mappings.service.WorkoutPlanService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.jdbc.Work;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class WorkoutPlanServiceImpl implements WorkoutPlanService {

    private final WorkoutPlanRepository workoutPlanRepository;
    private final UserRepository userRepository;
    private final TrainerRepository trainerRepository;
    private final ModelMapper modelMapper;
    private final CurrentUserService currentUserService;

    @Override
    public Response createWorkoutPlan(WorkoutPlanDTO dto) {
        enforceTrainerOrAdmin();

        WorkoutPlan plan = modelMapper.map(dto, WorkoutPlan.class);

        if (dto.getUserId() != null) {
            User user = userRepository.findById(dto.getUserId())
                    .orElseThrow(() -> new NotFoundException("Member user not found"));
            plan.setMemberWorkoutPlan(user);
        }

        if (dto.getTrainerId() != null) {
            Trainer trainer = trainerRepository.findById(dto.getTrainerId())
                    .orElseThrow(() -> new NotFoundException("Trainer not found"));
            plan.setTrainer(trainer);
        }

        workoutPlanRepository.save(plan);

        return Response.builder().status(200).message("Workout plan created and linked").build();
    }

    @Override
    public Response getAllWorkoutPlans() {
        List<WorkoutPlan> plans = workoutPlanRepository.findAll(Sort.by(Sort.Direction.DESC, "planId"));
        List<WorkoutPlanDTO> dtoList = plans.stream().map(plan -> {
            WorkoutPlanDTO dto = modelMapper.map(plan, WorkoutPlanDTO.class);
            if (plan.getMemberWorkoutPlan() != null) dto.setUserId(plan.getMemberWorkoutPlan().getId());
            if (plan.getTrainer() != null) dto.setTrainerId(plan.getTrainer().getTrainerId());
            return dto;
        }).toList();

        return Response.builder().status(200).message("success").workoutPlans(dtoList).build();
    }

    @Override
    public Response getWorkoutPlanById(Long id) {
        WorkoutPlan plan = workoutPlanRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Workout plan not found"));

        enforceAccessFor(plan);

        WorkoutPlanDTO dto = modelMapper.map(plan, WorkoutPlanDTO.class);
        if (plan.getMemberWorkoutPlan() != null) dto.setUserId(plan.getMemberWorkoutPlan().getId());
        if (plan.getTrainer() != null) dto.setTrainerId(plan.getTrainer().getTrainerId());

        return Response.builder().status(200).message("success").workoutPlan(dto).build();
    }

    @Override
    public Response updateWorkoutPlan(Long id, WorkoutPlanDTO dto) {
        WorkoutPlan plan = workoutPlanRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Workout plan not found"));

        enforceAccessFor(plan);
        modelMapper.map(dto, plan);

        if (dto.getUserId() != null) {
            User user = userRepository.findById(dto.getUserId())
                    .orElseThrow(() -> new NotFoundException("Member user not found"));
            plan.setMemberWorkoutPlan(user);
        }

        if (dto.getTrainerId() != null) {
            Trainer trainer = trainerRepository.findById(dto.getTrainerId())
                    .orElseThrow(() -> new NotFoundException("Trainer not found"));
            plan.setTrainer(trainer);
        }

        workoutPlanRepository.save(plan);

        return Response.builder().status(200).message("Workout plan updated").build();
    }

    @Override
    public Response deleteWorkoutPlan(Long id) {
        WorkoutPlan plan = workoutPlanRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Workout plan not found"));

        enforceAdminOnly();
        workoutPlanRepository.delete(plan);

        return Response.builder().status(200).message("Workout plan deleted").build();
    }

    @Override
    public Response getWorkoutPlansByTrainer(Long trainerUserId) {
        // ✅ Corrected to match repository
        List<WorkoutPlan> plans = workoutPlanRepository.findByTrainerUserId(trainerUserId);
        List<WorkoutPlanDTO> dtoList = plans.stream().map(plan -> {
            WorkoutPlanDTO dto = modelMapper.map(plan, WorkoutPlanDTO.class);
            if (plan.getMemberWorkoutPlan() != null) dto.setUserId(plan.getMemberWorkoutPlan().getId());
            if (plan.getTrainer() != null) dto.setTrainerId(plan.getTrainer().getTrainerId());
            return dto;
        }).toList();

        return Response.builder()
                .status(200)
                .message("Workout plans fetched for trainer")
                .workoutPlans(dtoList)
                .build();
    }

    // --- Security Checks ---
    private void enforceAccessFor(WorkoutPlan plan) {
        String currentUser = currentUserService.getCurrentUsername();

        boolean isAdmin = isRole("ROLE_ADMIN");
        boolean isTrainer = isRole("ROLE_TRAINER");
        boolean isMember = isRole("ROLE_MEMBER");

        if (isAdmin) return;

        if (isTrainer && plan.getTrainer() != null &&
                plan.getTrainer().getUser().getEmail().equalsIgnoreCase(currentUser)) return;

        if (isMember && plan.getMemberWorkoutPlan() != null &&
                plan.getMemberWorkoutPlan().getEmail().equalsIgnoreCase(currentUser)) return;

        throw new SecurityException("Access denied to this workout plan.");
    }

    private void enforceAdminOnly() {
        if (!isRole("ROLE_ADMIN")) {
            throw new SecurityException("Admin-only access.");
        }
    }

    private void enforceTrainerOrAdmin() {
        if (!isRole("ROLE_ADMIN") && !isRole("ROLE_TRAINER")) {
            throw new SecurityException("Only admins or trainers can create/update.");
        }
    }

    private boolean isRole(String role) {
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities()
                .stream().anyMatch(auth -> auth.getAuthority().equals(role));
    }
}
