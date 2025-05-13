package com.example.advanced_mappings.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClassEnrollment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "enrollment_id")
    private Long enrollmentId;

    @Temporal(TemporalType.DATE)
    @Column(name = "enrollment_date", nullable = false)
    private Date enrollmentDate;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "class_id",
            referencedColumnName = "class_id",
            foreignKey = @ForeignKey(name = "FK_enrollment_class_id")
    )
    private Class classes;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "member_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "FK_enrollment_member_id")
    )
    private User memberClassEnrollment;
}
