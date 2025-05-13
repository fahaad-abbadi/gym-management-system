// EquipmentDTO.java
package com.example.advanced_mappings.dtos;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EquipmentDTO {
    private Long equipmentId;
    private String equipmentName;
}
