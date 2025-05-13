package com.example.advanced_mappings.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long itemId;

    @Column(name = "item_name", nullable = false)
    private String itemName;

    @Column(nullable = false)
    private Long quantity;

    @Column(name = "purchase_price", nullable = false)
    private Double purchasePrice;

    @Column(name = "selling_price", nullable = false)
    private Double sellingPrice;
}

