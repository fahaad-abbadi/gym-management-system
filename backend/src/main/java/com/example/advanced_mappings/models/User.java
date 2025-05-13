// src/main/java/com/example/advanced_mappings/models/User.java
package com.example.advanced_mappings.models;

import com.example.advanced_mappings.enums.ApprovalStatus;
import com.example.advanced_mappings.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String userName;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @NotBlank
    @Column(name = "phone_number")
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    @Column(name = "approval_status")
    private ApprovalStatus approvalStatus = ApprovalStatus.PENDING;

    /** for members: who their trainer is */
    @ManyToOne
    @JoinColumn(name = "assigned_trainer_id")
    private User assignedTrainer;

    /** role-specific profiles */
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Member memberProfile;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Trainer trainerProfile;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Staff staffProfile;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Membership> membershipList = new ArrayList<>();

    @OneToMany(mappedBy = "memberWorkoutPlan", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WorkoutPlan> workoutPlanList = new ArrayList<>();

    @OneToMany(mappedBy = "memberWorkoutSession", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WorkoutSession> workoutSessionList = new ArrayList<>();

    @OneToMany(mappedBy = "memberAttendance", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Attendance> attendanceList = new ArrayList<>();

    @OneToMany(mappedBy = "memberClassEnrollment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ClassEnrollment> classEnrollmentList = new ArrayList<>();

    @OneToMany(mappedBy = "memberDietPlan", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DietPlan> dietPlanList = new ArrayList<>();

    @OneToMany(mappedBy = "trainer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TrainerSchedule> trainerScheduleList = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TrainerSchedule> attendedScheduleList = new ArrayList<>();


    public void addMembership(Membership m) {
        membershipList.add(m);
        m.setMember(this);
    }

    public void addWorkoutPlan(WorkoutPlan p) {
        workoutPlanList.add(p);
        p.setMemberWorkoutPlan(this);
    }

    public void addWorkoutSession(WorkoutSession s) {
        workoutSessionList.add(s);
        s.setMemberWorkoutSession(this);
    }

    public void addAttendance(Attendance a) {
        attendanceList.add(a);
        a.setMemberAttendance(this);
    }

    public void addClassEnrollment(ClassEnrollment ce) {
        classEnrollmentList.add(ce);
        ce.setMemberClassEnrollment(this);
    }

    public void addDietPlan(DietPlan d) {
        dietPlanList.add(d);
        d.setMemberDietPlan(this);
    }

    public void addTrainerSchedule(TrainerSchedule ts) {
        trainerScheduleList.add(ts);
        ts.setTrainer(this);
    }

    public void addAttendedSchedule(TrainerSchedule ts) {
        attendedScheduleList.add(ts);
        ts.setMember(this);
    }

}
