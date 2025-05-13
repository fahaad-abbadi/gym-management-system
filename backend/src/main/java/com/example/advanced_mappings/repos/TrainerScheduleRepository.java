package com.example.advanced_mappings.repos;

import com.example.advanced_mappings.models.TrainerSchedule;
import com.example.advanced_mappings.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface TrainerScheduleRepository extends JpaRepository<TrainerSchedule, Long> {
    List<TrainerSchedule> findByTrainer(User trainer);
    List<TrainerSchedule> findByMember(User member);
    List<TrainerSchedule> findByTrainerAndSessionDateAfter(User trainer, Date sessionDate);   // ✅ Corrected addition
}
