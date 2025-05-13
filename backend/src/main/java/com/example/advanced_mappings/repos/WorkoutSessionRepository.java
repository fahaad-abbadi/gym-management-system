package com.example.advanced_mappings.repos;

import com.example.advanced_mappings.models.WorkoutSession;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkoutSessionRepository extends JpaRepository<WorkoutSession, Long> {
}
