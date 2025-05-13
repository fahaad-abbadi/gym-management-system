package com.example.advanced_mappings.service;

import com.example.advanced_mappings.dtos.AttendanceDTO;
import com.example.advanced_mappings.dtos.Response;

public interface AttendanceService {
    Response createAttendance(AttendanceDTO dto);

    Response getAllAttendances();

    Response getAttendanceById(Long id);

    Response updateAttendance(Long id, AttendanceDTO dto);

    Response deleteAttendance(Long id);
}
