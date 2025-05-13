package com.example.advanced_mappings.repos;

import com.example.advanced_mappings.models.Membership;
import com.example.advanced_mappings.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MembershipRepository extends JpaRepository<Membership, Long> {
    List<Membership> findByMember(User user);
}
