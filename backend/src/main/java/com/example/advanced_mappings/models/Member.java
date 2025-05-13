// src/main/java/com/example/advanced_mappings/models/Member.java
package com.example.advanced_mappings.models;

import com.example.advanced_mappings.enums.Gender;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long memberId;

    /** link back to shared User table for name/email/phone/role/etc */
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /** profile-specific fields */
    @Column(name = "emergency_contact_name")
    private String emergencyContactName;

    @Column(name = "emergency_contact_phone")
    private String emergencyContactPhone;

    @Temporal(TemporalType.DATE)
    @Column(name = "date_of_birth")
    private Date dateOfBirth;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Temporal(TemporalType.DATE)
    @Column(name = "join_date")
    private Date joinDate;

    private String address;
    private String city;
    private String state;

    @Column(name = "postal_code")
    private String postalCode;
}
