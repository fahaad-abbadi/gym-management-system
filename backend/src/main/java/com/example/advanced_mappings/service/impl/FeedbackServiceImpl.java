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

import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class FeedbackServiceImpl implements FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final UserRepository userRepository;

    @Override
    public Response createFeedback(FeedbackDTO feedbackDTO) {
        User member = userRepository.findById(feedbackDTO.getUserId())
                .orElseThrow(() -> new NotFoundException("Member user not found"));

        User trainer = userRepository.findById(feedbackDTO.getTrainerId())
                .orElseThrow(() -> new NotFoundException("Trainer user not found"));

        Feedback feedback = Feedback.builder()
                .feedbackText(feedbackDTO.getFeedbackText())
                .rating(feedbackDTO.getRating())
                .feedbackDate(new Date())
                .member(member)
                .trainer(trainer)
                .build();

        feedbackRepository.save(feedback);

        return Response.builder()
                .status(200)
                .message("Feedback saved and linked")
                .build();
    }

    @Override
    public Response getAllFeedbacks() {
        List<Feedback> feedbacks = feedbackRepository.findAll(Sort.by(Sort.Direction.DESC, "feedbackId"));
        List<FeedbackDTO> dtoList = feedbacks.stream().map(fb -> FeedbackDTO.builder()
                .feedbackId(fb.getFeedbackId())
                .feedbackText(fb.getFeedbackText())
                .rating(fb.getRating())
                .feedbackDate(fb.getFeedbackDate())
                .memberName(fb.getMember().getUserName())
                .trainerName(fb.getTrainer().getUserName())
                .build()).toList();

        return Response.builder()
                .status(200)
                .message("success")
                .feedbacks(dtoList)
                .build();
    }

    @Override
    public Response getFeedbackById(Long id) {
        Feedback fb = feedbackRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Feedback not found"));

        FeedbackDTO dto = FeedbackDTO.builder()
                .feedbackId(fb.getFeedbackId())
                .feedbackText(fb.getFeedbackText())
                .rating(fb.getRating())
                .feedbackDate(fb.getFeedbackDate())
                .memberName(fb.getMember().getUserName())
                .trainerName(fb.getTrainer().getUserName())
                .build();

        return Response.builder()
                .status(200)
                .message("success")
                .feedback(dto)
                .build();
    }

    @Override
    public Response updateFeedback(Long id, FeedbackDTO feedbackDTO) {
        Feedback fb = feedbackRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Feedback Not Found"));

        fb.setFeedbackText(feedbackDTO.getFeedbackText());
        fb.setRating(feedbackDTO.getRating());

        if (feedbackDTO.getUserId() != null) {
            User member = userRepository.findById(feedbackDTO.getUserId())
                    .orElseThrow(() -> new NotFoundException("Member user not found"));
            fb.setMember(member);
        }

        if (feedbackDTO.getTrainerId() != null) {
            User trainer = userRepository.findById(feedbackDTO.getTrainerId())
                    .orElseThrow(() -> new NotFoundException("Trainer user not found"));
            fb.setTrainer(trainer);
        }

        feedbackRepository.save(fb);

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


