package com.example.advanced_mappings.repos;

import com.example.advanced_mappings.models.User;
import com.example.advanced_mappings.enums.Role;
import com.example.advanced_mappings.enums.ApprovalStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmailIgnoreCase(String email);
    List<User> findByRole(Role role);
    List<User> findByAssignedTrainerId(Long trainerId);
    List<User> findByApprovalStatus(ApprovalStatus status);
}
