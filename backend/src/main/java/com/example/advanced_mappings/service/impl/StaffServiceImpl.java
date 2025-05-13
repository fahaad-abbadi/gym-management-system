package com.example.advanced_mappings.service.impl;

import com.example.advanced_mappings.dtos.Response;
import com.example.advanced_mappings.dtos.StaffDTO;
import com.example.advanced_mappings.exceptions.NotFoundException;
import com.example.advanced_mappings.models.Staff;
import com.example.advanced_mappings.models.User;
import com.example.advanced_mappings.repos.StaffRepository;
import com.example.advanced_mappings.repos.UserRepository;
import com.example.advanced_mappings.service.StaffService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class StaffServiceImpl implements StaffService {

    private final StaffRepository staffRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public Response createStaff(StaffDTO dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new NotFoundException("User not found"));

        Staff staff = modelMapper.map(dto, Staff.class);
        staff.setUser(user);

        staffRepository.save(staff);

        return Response.builder().status(200).message("Staff saved and linked to user").build();
    }

    @Override
    public Response getAllStaff() {
        List<Staff> all = staffRepository.findAll(Sort.by(Sort.Direction.DESC, "staffId"));

        List<StaffDTO> dtoList = all.stream().map(staff -> {
            StaffDTO dto = modelMapper.map(staff, StaffDTO.class);
            dto.setUserId(staff.getUser().getId());
            dto.setUserName(staff.getUser().getUserName());
            dto.setEmail(staff.getUser().getEmail());
            dto.setPhoneNumber(staff.getUser().getPhoneNumber());
            return dto;
        }).toList();

        return Response.builder().status(200).message("success").staffList(dtoList).build();
    }

    @Override
    public Response getStaffById(Long id) {
        Staff staff = staffRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Staff not found"));

        StaffDTO dto = modelMapper.map(staff, StaffDTO.class);
        dto.setUserId(staff.getUser().getId());
        dto.setUserName(staff.getUser().getUserName());
        dto.setEmail(staff.getUser().getEmail());
        dto.setPhoneNumber(staff.getUser().getPhoneNumber());

        return Response.builder().status(200).message("success").staff(dto).build();
    }

    @Override
    public Response updateStaff(Long id, StaffDTO dto) {
        Staff staff = staffRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Staff not found"));

        modelMapper.map(dto, staff);

        if (dto.getUserId() != null) {
            User user = userRepository.findById(dto.getUserId())
                    .orElseThrow(() -> new NotFoundException("User not found"));
            staff.setUser(user);
        }

        staffRepository.save(staff);

        return Response.builder().status(200).message("Staff updated").build();
    }

    @Override
    public Response deleteStaff(Long id) {
        Staff staff = staffRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Staff not found"));

        staffRepository.delete(staff);

        return Response.builder().status(200).message("Staff deleted").build();
    }
}
