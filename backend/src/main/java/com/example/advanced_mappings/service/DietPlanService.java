package com.example.advanced_mappings.service;

import com.example.advanced_mappings.dtos.DietPlanDTO;
import com.example.advanced_mappings.dtos.Response;

public interface DietPlanService {
    Response createDietPlan(DietPlanDTO dietPlanDTO);
    Response getAllDietPlans();
    Response getDietPlanById(Long id);
    Response updateDietPlan(Long id, DietPlanDTO dietPlanDTO);
    Response deleteDietPlan(Long id);
}
