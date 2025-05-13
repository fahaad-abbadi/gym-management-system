package com.example.advanced_mappings.service.impl;

import com.example.advanced_mappings.dtos.FeedbackDTO;
import com.example.advanced_mappings.dtos.Response;
import com.example.advanced_mappings.exceptions.NotFoundException;
import com.example.advanced_mappings.models.Feedback;
import com.example.advanced_mappings.models.User;
import com.example.advanced_mappings.repos.FeedbackRepository;
import com.example.advanced_mappings.repos.UserRepository;
import com.example.advanced_mappings.service.FeedbackService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class FeedbackServiceImpl implements FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public Response createFeedback(FeedbackDTO feedbackDTO) {
        // Fetch member (user)
        User member = userRepository.findById(feedbackDTO.getUserId())
                .orElseThrow(() -> new NotFoundException("Member user not found"));

        // Fetch trainer (user)
        User trainer = userRepository.findById(feedbackDTO.getTrainerId())
                .orElseThrow(() -> new NotFoundException("Trainer user not found"));

        // Map DTO to entity
        Feedback feedback = modelMapper.map(feedbackDTO, Feedback.class);

        // Set relationships explicitly
        feedback.setMember(member);
        feedback.setTrainer(trainer);

        feedbackRepository.save(feedback);

        return Response.builder()
                .status(200)
                .message("Feedback saved and linked")
                .build();
    }

    @Override
    public Response getAllFeedbacks() {
        List<Feedback> feedbacks = feedbackRepository.findAll(Sort.by(Sort.Direction.DESC, "feedbackId"));
        List<FeedbackDTO> dtoList = feedbacks.stream().map(fb -> {
            FeedbackDTO dto = modelMapper.map(fb, FeedbackDTO.class);
            dto.setUserId(fb.getMember().getId());
            dto.setTrainerId(fb.getTrainer().getId());
            return dto;
        }).toList();

        return Response.builder()
                .status(200)
                .message("success")
                .feedbacks(dtoList)
                .build();
    }

    @Override
    public Response getFeedbackById(Long id) {
        Feedback feedback = feedbackRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Feedback not found"));

        FeedbackDTO feedbackDTO = modelMapper.map(feedback, FeedbackDTO.class);
        feedbackDTO.setUserId(feedback.getMember().getId());
        feedbackDTO.setTrainerId(feedback.getTrainer().getId());

        return Response.builder()
                .status(200)
                .message("success")
                .feedback(feedbackDTO)
                .build();
    }

    @Override
    public Response updateFeedback(Long id, FeedbackDTO feedbackDTO) {
        Feedback feedback = feedbackRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Feedback Not Found"));

        modelMapper.map(feedbackDTO, feedback);

        // Ensure correct user/trainer assignment during update
        if (feedbackDTO.getUserId() != null) {
            User member = userRepository.findById(feedbackDTO.getUserId())
                    .orElseThrow(() -> new NotFoundException("Member user not found"));
            feedback.setMember(member);
        }

        if (feedbackDTO.getTrainerId() != null) {
            User trainer = userRepository.findById(feedbackDTO.getTrainerId())
                    .orElseThrow(() -> new NotFoundException("Trainer user not found"));
            feedback.setTrainer(trainer);
        }

        feedbackRepository.save(feedback);

        return Response.builder()
                .status(200)
                .message("Feedback updated")
                .build();
    }

    @Override
    public Response deleteFeedback(Long id) {
        Feedback feedback = feedbackRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Feedback Not Found"));

        feedbackRepository.delete(feedback);

        return Response.builder()
                .status(200)
                .message("Feedback deleted")
                .build();
    }
}

