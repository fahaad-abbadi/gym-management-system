package com.example.advanced_mappings.repos;

import com.example.advanced_mappings.models.ClassEnrollment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClassEnrollmentRepository extends JpaRepository<ClassEnrollment, Long> {
}
