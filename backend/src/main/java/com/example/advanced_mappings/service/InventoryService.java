package com.example.advanced_mappings.service;

import com.example.advanced_mappings.dtos.InventoryDTO;
import com.example.advanced_mappings.dtos.Response;
import com.example.advanced_mappings.models.Inventory;

public interface InventoryService {
    Response createInventory(InventoryDTO inventoryDTO);
    Response getAllInventories();
    Response getInventoryById(Long id);
    Response updateInventory(Long id, InventoryDTO inventoryDTO);
    Response deleteInventory(Long id);
}
