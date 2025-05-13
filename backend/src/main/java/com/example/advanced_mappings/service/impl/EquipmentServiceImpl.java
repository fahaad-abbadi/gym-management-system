package com.example.advanced_mappings.service.impl;

import com.example.advanced_mappings.dtos.EquipmentDTO;
import com.example.advanced_mappings.dtos.Response;
import com.example.advanced_mappings.exceptions.NotFoundException;
import com.example.advanced_mappings.models.Equipment;
import com.example.advanced_mappings.repos.EquipmentRepository;
import com.example.advanced_mappings.service.EquipmentService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class EquipmentServiceImpl implements EquipmentService {

    private final EquipmentRepository equipmentRepository;
    private final ModelMapper modelMapper;

    @Override
    public Response createEquipment(EquipmentDTO equipmentDTO) {
        Equipment equipment = modelMapper.map(equipmentDTO, Equipment.class);

        equipmentRepository.save(equipment);

        return Response.builder()
                .status(200)
                .message("success")
                .build();
    }

    @Override
    public Response getAllEquipments() {
        List<Equipment> items = equipmentRepository.findAll(Sort.by(Sort.Direction.DESC, "equipmentId"));
        List<EquipmentDTO> dtoList = modelMapper.map(items, new TypeToken<List<EquipmentDTO>>() {}.getType());

        return Response.builder()
                .status(200)
                .message("success")
                .equipments(dtoList)
                .build();
    }

    @Override
    public Response getEquipmentById(Long id) {
        Equipment item = equipmentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Equipment Not Found"));

        EquipmentDTO dto = modelMapper.map(item, EquipmentDTO.class);

        return Response.builder()
                .status(200)
                .message("success")
                .equipment(dto)
                .build();
    }

    @Override
    public Response updateEquipment(Long id, EquipmentDTO equipmentDTO) {
        Equipment item = equipmentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Equipment Not Found"));

        modelMapper.map(equipmentDTO, item);
        equipmentRepository.save(item);

        return Response.builder()
                .status(200)
                .message("Equipment updated")
                .build();
    }

    @Override
    public Response deleteEquipment(Long id) {
        equipmentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Equipment Not Found"));

        equipmentRepository.deleteById(id);

        return Response.builder()
                .status(200)
                .message("Equipment Deleted")
                .build();
    }
}
