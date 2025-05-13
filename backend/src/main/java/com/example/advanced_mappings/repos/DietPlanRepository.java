package com.example.advanced_mappings.repos;

import com.example.advanced_mappings.models.DietPlan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DietPlanRepository extends JpaRepository<DietPlan, Long> {
}
