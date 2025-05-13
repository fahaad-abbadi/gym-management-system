package com.example.advanced_mappings.service;

import com.example.advanced_mappings.dtos.EquipmentDTO;
import com.example.advanced_mappings.dtos.Response;

public interface EquipmentService {
    Response createEquipment(EquipmentDTO equipmentDTO);
    Response getAllEquipments();
    Response getEquipmentById(Long id);
    Response updateEquipment(Long id, EquipmentDTO equipmentDTO);
    Response deleteEquipment(Long id);
}
