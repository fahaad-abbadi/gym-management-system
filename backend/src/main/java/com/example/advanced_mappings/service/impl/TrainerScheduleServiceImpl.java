package com.example.advanced_mappings.service.impl;

import com.example.advanced_mappings.dtos.Response;
import com.example.advanced_mappings.dtos.TrainerScheduleDTO;
import com.example.advanced_mappings.exceptions.NotFoundException;
import com.example.advanced_mappings.models.TrainerSchedule;
import com.example.advanced_mappings.models.User;
import com.example.advanced_mappings.repos.TrainerScheduleRepository;
import com.example.advanced_mappings.repos.UserRepository;
import com.example.advanced_mappings.service.TrainerScheduleService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class TrainerScheduleServiceImpl implements TrainerScheduleService {

    private final TrainerScheduleRepository trainerScheduleRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public Response createTrainerSchedule(TrainerScheduleDTO trainerScheduleDTO) {
        User trainer = userRepository.findById(trainerScheduleDTO.getTrainerUserId())
                .orElseThrow(() -> new NotFoundException("Trainer user not found"));

        User member = userRepository.findById(trainerScheduleDTO.getMemberUserId())
                .orElseThrow(() -> new NotFoundException("Member user not found"));

        TrainerSchedule trainerSchedule = modelMapper.map(trainerScheduleDTO, TrainerSchedule.class);
        trainerSchedule.setTrainer(trainer);
        trainerSchedule.setMember(member);

        trainerScheduleRepository.save(trainerSchedule);

        return Response.builder()
                .status(200)
                .message("Trainer Schedule saved and linked")
                .build();
    }

    @Override
    public Response getAllTrainerSchedules() {
        List<TrainerSchedule> trainerSchedules = trainerScheduleRepository.findAll(Sort.by(Sort.Direction.DESC, "scheduleId"));

        List<TrainerScheduleDTO> dtoList = trainerSchedules.stream().map(schedule -> {
            TrainerScheduleDTO dto = modelMapper.map(schedule, TrainerScheduleDTO.class);
            dto.setTrainerUserId(schedule.getTrainer().getId());
            dto.setMemberUserId(schedule.getMember().getId());
            return dto;
        }).toList();

        return Response.builder()
                .status(200)
                .message("success")
                .trainerSchedules(dtoList)
                .build();
    }

    @Override
    public Response getTrainerScheduleById(Long id) {
        TrainerSchedule schedule = trainerScheduleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Trainer Schedule Not Found"));

        TrainerScheduleDTO dto = modelMapper.map(schedule, TrainerScheduleDTO.class);
        dto.setTrainerUserId(schedule.getTrainer().getId());
        dto.setMemberUserId(schedule.getMember().getId());

        return Response.builder()
                .status(200)
                .message("success")
                .trainerSchedule(dto)
                .build();
    }

    @Override
    public Response updateTrainerSchedule(Long id, TrainerScheduleDTO trainerScheduleDTO) {
        TrainerSchedule schedule = trainerScheduleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Trainer Schedule Not Found"));

        modelMapper.map(trainerScheduleDTO, schedule);

        if (trainerScheduleDTO.getTrainerUserId() != null) {
            User trainer = userRepository.findById(trainerScheduleDTO.getTrainerUserId())
                    .orElseThrow(() -> new NotFoundException("Trainer user not found"));
            schedule.setTrainer(trainer);
        }

        if (trainerScheduleDTO.getMemberUserId() != null) {
            User member = userRepository.findById(trainerScheduleDTO.getMemberUserId())
                    .orElseThrow(() -> new NotFoundException("Member user not found"));
            schedule.setMember(member);
        }

        trainerScheduleRepository.save(schedule);

        return Response.builder()
                .status(200)
                .message("Trainer Schedule updated")
                .build();
    }

    @Override
    public Response deleteTrainerSchedule(Long id) {
        TrainerSchedule schedule = trainerScheduleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Trainer Schedule Not Found"));

        trainerScheduleRepository.delete(schedule);

        return Response.builder()
                .status(200)
                .message("Trainer Schedule deleted")
                .build();
    }

    @Override
    public Response getUpcomingSessionsByTrainer(Long trainerUserId) {
        User trainer = userRepository.findById(trainerUserId)
                .orElseThrow(() -> new NotFoundException("Trainer user not found"));

        List<TrainerSchedule> schedules = trainerScheduleRepository
                .findByTrainerAndSessionDateAfter(trainer, new Date());

        List<TrainerScheduleDTO> dtoList = schedules.stream().map(schedule -> {
            TrainerScheduleDTO dto = modelMapper.map(schedule, TrainerScheduleDTO.class);
            dto.setTrainerUserId(schedule.getTrainer().getId());
            dto.setMemberUserId(schedule.getMember().getId());
            return dto;
        }).toList();

        return Response.builder()
                .status(200)
                .message("success")
                .trainerSchedules(dtoList)
                .build();
    }
}

