// src/main/java/com/example/advanced_mappings/dtos/Response.java
package com.example.advanced_mappings.dtos;

import com.example.advanced_mappings.enums.Role;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response {

    // Generic
    private int status;
    private String message;

    // For login responses
    private String token;
    private Role role;
    private String expirationTime;

    // For pagination (optional)
    private Integer totalPages;
    private Long totalElements;

    // Data output - Single entity returns
    private UserDTO user;
    private TrainerDTO trainer;
    private MemberDTO member;
    private StaffDTO staff;
    private WorkoutPlanDTO workoutPlan;
    private WorkoutSessionDTO workoutSession;
    private AttendanceDTO attendance;
    private ClassEnrollmentDTO classEnrollment;
    private EquipmentDTO equipment;
    private ClassDTO clazz;
    private FeedbackDTO feedback;
    private DietPlanDTO dietPlan;
    private InventoryDTO inventory;
    private MembershipDTO membership;
    private TrainerScheduleDTO trainerSchedule;

    // Data output - Collection returns
    private List<UserDTO> users;
    private List<TrainerDTO> trainers;
    private List<MemberDTO> members;
    private List<StaffDTO> staffList;
    private List<WorkoutPlanDTO> workoutPlans;
    private List<WorkoutSessionDTO> workoutSessions;
    private List<AttendanceDTO> attendances;
    private List<ClassEnrollmentDTO> classEnrollments;
    private List<EquipmentDTO> equipments;
    private List<ClassDTO> classes;
    private List<FeedbackDTO> feedbacks;
    private List<DietPlanDTO> dietPlans;
    private List<InventoryDTO> inventories;
    private List<MembershipDTO> memberships;
    private List<TrainerScheduleDTO> trainerSchedules;

    // Special / computed returns
    private Double averageRating;
    private Double paidRevenue;

    // Timestamp (optional for consistency)
    private final LocalDateTime timestamp = LocalDateTime.now();
}
