package com.example.advanced_mappings.service.impl;

import com.example.advanced_mappings.dtos.DietPlanDTO;
import com.example.advanced_mappings.dtos.Response;
import com.example.advanced_mappings.exceptions.NotFoundException;
import com.example.advanced_mappings.models.DietPlan;
import com.example.advanced_mappings.models.Trainer;
import com.example.advanced_mappings.models.User;
import com.example.advanced_mappings.repos.DietPlanRepository;
import com.example.advanced_mappings.repos.TrainerRepository;
import com.example.advanced_mappings.repos.UserRepository;
import com.example.advanced_mappings.security.CurrentUserService;
import com.example.advanced_mappings.service.DietPlanService;
import lombok.AllArgsConstructor;
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
public class DietPlanServiceImpl implements DietPlanService {

    private final DietPlanRepository dietPlanRepository;
    private final UserRepository userRepository;
    private final TrainerRepository trainerRepository;
    private final ModelMapper modelMapper;
    private final CurrentUserService currentUserService;

    @Override
    public Response createDietPlan(DietPlanDTO dto) {
        enforceTrainerOrAdmin();

        DietPlan plan = modelMapper.map(dto, DietPlan.class);

        if (dto.getUserId() != null) {
            User user = userRepository.findById(dto.getUserId())
                    .orElseThrow(() -> new NotFoundException("Member user not found"));
            plan.setMemberDietPlan(user);
        }

        if (dto.getTrainerId() != null) {
            Trainer trainer = trainerRepository.findById(dto.getTrainerId())
                    .orElseThrow(() -> new NotFoundException("Trainer not found"));
            plan.setTrainerDietPlan(trainer);
        }

        dietPlanRepository.save(plan);

        return Response.builder().status(200).message("Diet plan created and linked").build();
    }

    @Override
    public Response updateDietPlan(Long id, DietPlanDTO dto) {
        DietPlan plan = dietPlanRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Diet plan not found"));

        enforceAccessFor(plan);

        modelMapper.map(dto, plan);

        if (dto.getUserId() != null) {
            User user = userRepository.findById(dto.getUserId())
                    .orElseThrow(() -> new NotFoundException("Member user not found"));
            plan.setMemberDietPlan(user);
        }

        if (dto.getTrainerId() != null) {
            Trainer trainer = trainerRepository.findById(dto.getTrainerId())
                    .orElseThrow(() -> new NotFoundException("Trainer not found"));
            plan.setTrainerDietPlan(trainer);
        }

        dietPlanRepository.save(plan);

        return Response.builder().status(200).message("Diet plan updated").build();
    }

    @Override
    public Response getDietPlanById(Long id) {
        DietPlan plan = dietPlanRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Diet plan not found"));

        enforceAccessFor(plan);

        DietPlanDTO dto = modelMapper.map(plan, DietPlanDTO.class);

        if (plan.getMemberDietPlan() != null) {
            dto.setUserId(plan.getMemberDietPlan().getId());
        }

        if (plan.getTrainerDietPlan() != null) {
            dto.setTrainerId(plan.getTrainerDietPlan().getTrainerId());
        }

        return Response.builder().status(200).message("success").dietPlan(dto).build();
    }

    @Override
    public Response getAllDietPlans() {
        List<DietPlan> plans = dietPlanRepository.findAll(Sort.by(Sort.Direction.DESC, "dietPlanId"));
        List<DietPlanDTO> dtoList = plans.stream().map(plan -> {
            DietPlanDTO dto = modelMapper.map(plan, DietPlanDTO.class);

            if (plan.getMemberDietPlan() != null) {
                dto.setUserId(plan.getMemberDietPlan().getId());
            }

            if (plan.getTrainerDietPlan() != null) {
                dto.setTrainerId(plan.getTrainerDietPlan().getTrainerId());
            }

            return dto;
        }).toList();

        return Response.builder().status(200).message("success").dietPlans(dtoList).build();
    }

    @Override
    public Response deleteDietPlan(Long id) {
        DietPlan plan = dietPlanRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Diet plan not found"));

        enforceAdminOnly();

        dietPlanRepository.delete(plan);

        return Response.builder().status(200).message("Diet plan deleted").build();
    }

    // --- Access Control Helpers ---
    private void enforceAccessFor(DietPlan plan) {
        String currentUser = currentUserService.getCurrentUsername();
        boolean isAdmin = isRole("ROLE_ADMIN");
        boolean isTrainer = isRole("ROLE_TRAINER");
        boolean isMember = isRole("ROLE_MEMBER");

        if (isAdmin) return;

        if (isTrainer && plan.getTrainerDietPlan() != null &&
                currentUser.equalsIgnoreCase(plan.getTrainerDietPlan().getUser().getEmail())) return;

        if (isMember && plan.getMemberDietPlan() != null &&
                currentUser.equalsIgnoreCase(plan.getMemberDietPlan().getEmail())) return;

        throw new SecurityException("You are not authorized to access this diet plan.");
    }

    private void enforceTrainerOrAdmin() {
        if (!isRole("ROLE_ADMIN") && !isRole("ROLE_TRAINER")) {
            throw new SecurityException("Only admins or trainers can create or update.");
        }
    }

    private void enforceAdminOnly() {
        if (!isRole("ROLE_ADMIN")) {
            throw new SecurityException("Only admins can delete.");
        }
    }

    private boolean isRole(String role) {
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities()
                .stream().anyMatch(auth -> auth.getAuthority().equals(role));
    }
}
