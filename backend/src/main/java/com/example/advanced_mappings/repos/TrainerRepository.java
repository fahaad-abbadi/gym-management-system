package com.example.advanced_mappings.repos;

import com.example.advanced_mappings.models.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TrainerRepository extends JpaRepository<Trainer, Long> {
    Optional<Trainer> findByUserId(Long userId);
}
