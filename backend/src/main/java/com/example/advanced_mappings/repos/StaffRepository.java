package com.example.advanced_mappings.repos;

import com.example.advanced_mappings.models.Staff;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StaffRepository extends JpaRepository<Staff, Long> {
}
