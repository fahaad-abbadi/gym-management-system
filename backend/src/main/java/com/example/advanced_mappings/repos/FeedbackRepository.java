package com.example.advanced_mappings.repos;

import com.example.advanced_mappings.models.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    @Query("SELECT AVG(f.rating) FROM Feedback f WHERE f.trainer.id = :trainerUserId")
    Double findAverageRatingForTrainer(@Param("trainerUserId") Long trainerUserId);

}
