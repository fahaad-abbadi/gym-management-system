package com.example.advanced_mappings.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Staff {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "staff_id")
    private Long staffId;

    // link back to the shared User account
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // domain-specific fields
    @Column(name = "position_title", nullable = false)
    private String positionTitle;

    @Temporal(TemporalType.DATE)
    @Column(name = "hire_date", nullable = false)
    private Date hireDate;

    @Column(nullable = false)
    private Double salary;
}
