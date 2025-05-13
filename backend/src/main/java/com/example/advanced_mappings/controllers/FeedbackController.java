package com.example.advanced_mappings.controllers;

import com.example.advanced_mappings.dtos.FeedbackDTO;
import com.example.advanced_mappings.dtos.Response;
import com.example.advanced_mappings.models.Feedback;
import com.example.advanced_mappings.repos.FeedbackRepository;
import com.example.advanced_mappings.service.FeedbackService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/feedback")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class FeedbackController {

    private final FeedbackService feedbackService;

    @PreAuthorize("hasAnyRole('ADMIN', 'MEMBER')")
    @PostMapping
    public ResponseEntity<Response> create(@RequestBody FeedbackDTO dto) {
        return ResponseEntity.ok(feedbackService.createFeedback(dto));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<Response> update(@PathVariable Long id, @RequestBody FeedbackDTO dto) {
        return ResponseEntity.ok(feedbackService.updateFeedback(id, dto));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MEMBER')")
    @GetMapping("/{id}")
    public ResponseEntity<Response> getById(@PathVariable Long id) {
        return ResponseEntity.ok(feedbackService.getFeedbackById(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<Response> getAll() {
        return ResponseEntity.ok(feedbackService.getAllFeedbacks());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Response> delete(@PathVariable Long id) {
        return ResponseEntity.ok(feedbackService.deleteFeedback(id));
    }
}

