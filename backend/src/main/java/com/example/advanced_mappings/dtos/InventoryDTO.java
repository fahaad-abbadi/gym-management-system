// InventoryDTO.java
package com.example.advanced_mappings.dtos;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InventoryDTO {
    private Long itemId;
    private String itemName;
    private Long quantity;
    private Double purchasePrice;
    private Double sellingPrice;
}
