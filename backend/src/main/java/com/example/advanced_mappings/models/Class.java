package com.example.advanced_mappings.models;

import com.example.advanced_mappings.enums.ClassType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Class {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "class_id")
    private Long classId;

    @Column(name = "class_name")
    private String className;

    @Enumerated(EnumType.STRING)
    private ClassType classType;

    @Column(name = "schedule_time")
    private LocalDateTime scheduleTime;

    @Column(name = "duration_minutes")
    private Long durationMinutes;

    @Column(name = "max_capacity")
    private Long maxCapacity;

    // Trainer who teaches this class
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "trainer_id",
            referencedColumnName = "trainer_id",
            foreignKey = @ForeignKey(name = "FK_class_trainer_id")
    )
    private Trainer trainerClass;

    @OneToMany(mappedBy = "classAttendance", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Attendance> attendanceList = new ArrayList<>();

    @OneToMany(mappedBy = "classes", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ClassEnrollment> classEnrollmentList = new ArrayList<>();
}
