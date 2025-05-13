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
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attendance_id")
    private Long attendanceId;

    @Temporal(TemporalType.DATE)
    @Column(name = "attendance_date", nullable = false)
    private Date attendanceDate;

    private Boolean attended;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "member_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "FK_attendance_member_id")
    )
    private User memberAttendance;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "class_id",
            referencedColumnName = "class_id",
            foreignKey = @ForeignKey(name = "FK_attendance_class_id")
    )
    private Class classAttendance;

}

