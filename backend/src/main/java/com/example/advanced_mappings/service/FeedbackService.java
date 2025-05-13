package com.example.advanced_mappings.service;

import com.example.advanced_mappings.dtos.FeedbackDTO;
import com.example.advanced_mappings.dtos.Response;

public interface FeedbackService {
    Response createFeedback(FeedbackDTO feedbackDTO);
    Response getAllFeedbacks();
    Response getFeedbackById(Long id);
    Response updateFeedback(Long id, FeedbackDTO feedbackDTO);
    Response deleteFeedback(Long id);
}
