package com.example.advanced_mappings.repos;

import com.example.advanced_mappings.models.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrainerRepository extends JpaRepository<Trainer, Long> {
    Trainer findByUserId(Long userId);
}
