package com.example.advanced_mappings.service.impl;

import com.example.advanced_mappings.dtos.Response;
import com.example.advanced_mappings.dtos.TrainerDTO;
import com.example.advanced_mappings.exceptions.NotFoundException;
import com.example.advanced_mappings.models.Trainer;
import com.example.advanced_mappings.models.User;
import com.example.advanced_mappings.repos.FeedbackRepository;
import com.example.advanced_mappings.repos.TrainerRepository;
import com.example.advanced_mappings.repos.UserRepository;
import com.example.advanced_mappings.security.CurrentUserService;
import com.example.advanced_mappings.service.TrainerService;
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
public class TrainerServiceImpl implements TrainerService {

    private final TrainerRepository trainerRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final CurrentUserService currentUserService;
    private final FeedbackRepository feedbackRepository;

    @Override
    public Response createTrainer(TrainerDTO dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new NotFoundException("Associated user not found"));

        Trainer trainer = modelMapper.map(dto, Trainer.class);
        trainer.setUser(user);

        trainerRepository.save(trainer);

        return Response.builder()
                .status(200)
                .message("Trainer created and linked to user")
                .build();
    }

    @Override
    public Response getAllTrainers() {
        List<Trainer> trainers = trainerRepository.findAll(Sort.by(Sort.Direction.DESC, "trainerId"));

        List<TrainerDTO> dtoList = trainers.stream().map(trainer -> {
            TrainerDTO dto = modelMapper.map(trainer, TrainerDTO.class);
            dto.setUserId(trainer.getUser().getId());
            dto.setUserName(trainer.getUser().getUserName());
            dto.setEmail(trainer.getUser().getEmail());
            dto.setPhoneNumber(trainer.getUser().getPhoneNumber());
            return dto;
        }).toList();

        return Response.builder()
                .status(200)
                .message("success")
                .trainers(dtoList)
                .build();
    }

    @Override
    public Response getTrainerById(Long id) {
        Trainer trainer = trainerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Trainer not found"));

        enforceTrainerOwnershipOrAdmin(trainer.getUser().getId());

        TrainerDTO dto = modelMapper.map(trainer, TrainerDTO.class);
        dto.setUserId(trainer.getUser().getId());
        dto.setUserName(trainer.getUser().getUserName());
        dto.setEmail(trainer.getUser().getEmail());
        dto.setPhoneNumber(trainer.getUser().getPhoneNumber());

        return Response.builder()
                .status(200)
                .message("success")
                .trainer(dto)
                .build();
    }

    @Override
    public Response updateTrainer(Long id, TrainerDTO dto) {
        Trainer trainer = trainerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Trainer Not Found"));

        trainer.setHireDate(dto.getHireDate());
        trainer.setSpeciality(dto.getSpeciality());

        trainerRepository.save(trainer);

        return Response.builder()
                .status(200)
                .message("Trainer successfully updated")
                .build();
    }

    @Override
    public Response deleteTrainer(Long id) {
        Trainer trainer = trainerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Trainer not found"));

        trainerRepository.delete(trainer);

        return Response.builder()
                .status(200)
                .message("Trainer deleted")
                .build();
    }

    @Override
    public Response getTrainerAverageRating(Long trainerId) {
        Double avg = feedbackRepository.findAverageRatingForTrainer(trainerId);
        Double roundedAvg = (avg != null) ? Math.round(avg * 10.0) / 10.0 : null;

        return Response.builder()
                .status(200)
                .message("success")
                .averageRating(roundedAvg)
                .build();
    }

    private void enforceTrainerOwnershipOrAdmin(Long trainerUserId) {
        Long currentUserId = currentUserService.getCurrentUserId();
        boolean isAdmin = SecurityContextHolder.getContext().getAuthentication().getAuthorities()
                .stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));

        if (!isAdmin && !trainerUserId.equals(currentUserId)) {
            throw new SecurityException("Access denied: not trainer owner or admin.");
        }
    }
}
