package com.example.advanced_mappings.service.impl;

import com.example.advanced_mappings.dtos.Response;
import com.example.advanced_mappings.dtos.WorkoutSessionDTO;
import com.example.advanced_mappings.exceptions.NotFoundException;
import com.example.advanced_mappings.models.User;
import com.example.advanced_mappings.models.WorkoutPlan;
import com.example.advanced_mappings.models.WorkoutSession;
import com.example.advanced_mappings.repos.UserRepository;
import com.example.advanced_mappings.repos.WorkoutPlanRepository;
import com.example.advanced_mappings.repos.WorkoutSessionRepository;
import com.example.advanced_mappings.service.WorkoutSessionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class WorkoutSessionServiceImpl implements WorkoutSessionService {

    private final WorkoutSessionRepository workoutSessionRepository;
    private final UserRepository userRepository;
    private final WorkoutPlanRepository workoutPlanRepository;
    private final ModelMapper modelMapper;

    @Override
    public Response createWorkoutSession(WorkoutSessionDTO dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new NotFoundException("Member user not found"));

        WorkoutPlan plan = workoutPlanRepository.findById(dto.getPlanId())
                .orElseThrow(() -> new NotFoundException("Workout plan not found"));

        WorkoutSession session = modelMapper.map(dto, WorkoutSession.class);
        session.setMemberWorkoutSession(user);
        session.setWorkoutPlanWorkoutSession(plan);

        workoutSessionRepository.save(session);

        return Response.builder()
                .status(200)
                .message("Workout session created and linked successfully")
                .build();
    }

    @Override
    public Response getAllWorkoutSessions() {
        List<WorkoutSession> sessions = workoutSessionRepository.findAll(Sort.by(Sort.Direction.DESC, "sessionId"));

        List<WorkoutSessionDTO> dtoList = sessions.stream().map(session -> {
            WorkoutSessionDTO dto = modelMapper.map(session, WorkoutSessionDTO.class);
            if (session.getMemberWorkoutSession() != null) dto.setUserId(session.getMemberWorkoutSession().getId());
            if (session.getWorkoutPlanWorkoutSession() != null) dto.setPlanId(session.getWorkoutPlanWorkoutSession().getPlanId());
            return dto;
        }).toList();

        return Response.builder()
                .status(200)
                .message("success")
                .workoutSessions(dtoList)
                .build();
    }

    @Override
    public Response getWorkoutSessionById(Long id) {
        WorkoutSession session = workoutSessionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Workout session not found"));

        WorkoutSessionDTO dto = modelMapper.map(session, WorkoutSessionDTO.class);
        if (session.getMemberWorkoutSession() != null) dto.setUserId(session.getMemberWorkoutSession().getId());
        if (session.getWorkoutPlanWorkoutSession() != null) dto.setPlanId(session.getWorkoutPlanWorkoutSession().getPlanId());

        return Response.builder()
                .status(200)
                .message("success")
                .workoutSession(dto)
                .build();
    }

    @Override
    public Response updateWorkoutSession(Long id, WorkoutSessionDTO dto) {
        WorkoutSession session = workoutSessionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Workout session not found"));

        modelMapper.map(dto, session);

        if (dto.getUserId() != null) {
            User user = userRepository.findById(dto.getUserId())
                    .orElseThrow(() -> new NotFoundException("Member user not found"));
            session.setMemberWorkoutSession(user);
        }

        if (dto.getPlanId() != null) {
            WorkoutPlan plan = workoutPlanRepository.findById(dto.getPlanId())
                    .orElseThrow(() -> new NotFoundException("Workout plan not found"));
            session.setWorkoutPlanWorkoutSession(plan);
        }

        workoutSessionRepository.save(session);

        return Response.builder()
                .status(200)
                .message("Workout session updated successfully")
                .build();
    }

    @Override
    public Response deleteWorkoutSession(Long id) {
        WorkoutSession session = workoutSessionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Workout session not found"));

        workoutSessionRepository.delete(session);

        return Response.builder()
                .status(200)
                .message("Workout session deleted successfully")
                .build();
    }
}

