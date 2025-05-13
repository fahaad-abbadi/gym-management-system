package com.example.advanced_mappings.service.impl;

import com.example.advanced_mappings.dtos.InventoryDTO;
import com.example.advanced_mappings.dtos.Response;
import com.example.advanced_mappings.exceptions.NotFoundException;
import com.example.advanced_mappings.models.Inventory;
import com.example.advanced_mappings.repos.InventoryRepository;
import com.example.advanced_mappings.service.InventoryService;
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
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;
    private final ModelMapper modelMapper;

    @Override
    public Response createInventory(InventoryDTO inventoryDTO) {
        Inventory inventory = modelMapper.map(inventoryDTO, Inventory.class);
        inventoryRepository.save(inventory);

        return Response.builder()
                .status(200)
                .message("Success")
                .build();
    }

    @Override
    public Response getAllInventories() {
        List<Inventory> items = inventoryRepository.findAll(Sort.by(Sort.Direction.DESC, "itemId"));
        List<InventoryDTO> dtoList = modelMapper.map(items, new TypeToken<List<InventoryDTO>>() {}.getType());

        return Response.builder()
                .status(200)
                .message("success")
                .inventories(dtoList)
                .build();
    }

    @Override
    public Response getInventoryById(Long id) {
        Inventory item = inventoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Inventory item not found"));

        InventoryDTO dto = modelMapper.map(item, InventoryDTO.class);

        return Response.builder()
                .status(200)
                .message("success")
                .inventory(dto)
                .build();
    }

    @Override
    public Response updateInventory(Long id, InventoryDTO inventoryDTO) {
        Inventory item = inventoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Inventory item not found"));

        modelMapper.map(inventoryDTO, item);
        inventoryRepository.save(item);

        return Response.builder()
                .status(200)
                .message("success")
                .build();
    }

    @Override
    public Response deleteInventory(Long id) {
        inventoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Inventory item not found"));

        inventoryRepository.deleteById(id);

        return Response.builder()
                .status(200)
                .message("success")
                .build();
    }
}
