package com.example.advanced_mappings.repos;

import com.example.advanced_mappings.models.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
}
