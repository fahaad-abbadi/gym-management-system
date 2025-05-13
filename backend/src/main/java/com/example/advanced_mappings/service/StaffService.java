package com.example.advanced_mappings.service;

import com.example.advanced_mappings.dtos.Response;
import com.example.advanced_mappings.dtos.StaffDTO;

public interface StaffService {

    Response createStaff(StaffDTO staffDTO);

    Response getAllStaff();

    Response getStaffById(Long id);

    Response updateStaff(Long id, StaffDTO staffDTO);

    Response deleteStaff(Long id);
}