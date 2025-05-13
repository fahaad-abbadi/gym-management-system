// MembershipDTO.java
package com.example.advanced_mappings.dtos;

import com.example.advanced_mappings.enums.PaymentStatus;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MembershipDTO {
    private Long membershipId;
    private Long userId;
    private Date startDate;
    private Date endDate;
    private Double price;
    private PaymentStatus paymentStatus;
}
