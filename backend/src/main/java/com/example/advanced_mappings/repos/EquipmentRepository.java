package com.example.advanced_mappings.repos;

import com.example.advanced_mappings.models.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EquipmentRepository extends JpaRepository<Equipment, Long> {
}
