package com.example.advanced_mappings.models;

import com.example.advanced_mappings.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "membership")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Membership {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "membership_id")
    private Long membershipId;

    /**
     * The user who holds this membership.
     * Must have role = MEMBER.
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User member;

    @Temporal(TemporalType.DATE)
    @Column(name = "start_date", nullable = false)
    private Date startDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "end_date", nullable = false)
    private Date endDate;

    @Column(nullable = false)
    private Double price;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status", nullable = false)
    private PaymentStatus paymentStatus;
}
