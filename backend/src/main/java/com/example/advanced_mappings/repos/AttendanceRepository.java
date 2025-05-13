package com.example.advanced_mappings.repos;

import com.example.advanced_mappings.models.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
}
