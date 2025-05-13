package com.example.advanced_mappings.service.impl;

import com.example.advanced_mappings.dtos.AttendanceDTO;
import com.example.advanced_mappings.dtos.Response;
import com.example.advanced_mappings.exceptions.NotFoundException;
import com.example.advanced_mappings.models.Attendance;
import com.example.advanced_mappings.models.User;
import com.example.advanced_mappings.repos.AttendanceRepository;
import com.example.advanced_mappings.repos.ClassRepository;
import com.example.advanced_mappings.repos.UserRepository;
import com.example.advanced_mappings.service.AttendanceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.example.advanced_mappings.models.Class;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AttendanceServiceImpl implements AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final UserRepository userRepository;
    private final ClassRepository classRepository;
    private final ModelMapper modelMapper;

    @Override
    public Response createAttendance(AttendanceDTO dto) {
        // Get the User (Member)
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new NotFoundException("Member user not found"));

        // Get the Class
        Class gymClass = classRepository.findById(dto.getClassId())
                .orElseThrow(() -> new NotFoundException("Class not found"));

        Attendance attendance = modelMapper.map(dto, Attendance.class);

        // Set relationships explicitly (safe and required)
        attendance.setMemberAttendance(user);
        attendance.setClassAttendance(gymClass);

        // Use User's helper method if you have it (optional)
        user.addAttendance(attendance);

        attendanceRepository.save(attendance);

        return Response.builder()
                .status(200)
                .message("Attendance saved and linked to user")
                .build();
    }

    @Override
    public Response getAllAttendances() {
        List<Attendance> all = attendanceRepository.findAll(Sort.by(Sort.Direction.DESC, "attendanceId"));
        List<AttendanceDTO> dtoList = modelMapper.map(all, new TypeToken<List<AttendanceDTO>>() {}.getType());
        return Response.builder()
                .status(200)
                .message("success")
                .attendances(dtoList)
                .build();
    }

    @Override
    public Response getAttendanceById(Long id) {
        Attendance attendance = attendanceRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Attendance not found"));
        AttendanceDTO dto = modelMapper.map(attendance, AttendanceDTO.class);
        return Response.builder()
                .status(200)
                .message("success")
                .attendance(dto)
                .build();
    }

    @Override
    public Response updateAttendance(Long id, AttendanceDTO dto) {
        Attendance attendance = attendanceRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Attendance not found"));

        modelMapper.map(dto, attendance);

        // Ensure user and class are still set if changed
        if (dto.getUserId() != null) {
            User user = userRepository.findById(dto.getUserId())
                    .orElseThrow(() -> new NotFoundException("Member user not found"));
            attendance.setMemberAttendance(user);
        }

        if (dto.getClassId() != null) {
            Class gymClass = classRepository.findById(dto.getClassId())
                    .orElseThrow(() -> new NotFoundException("Class not found"));
            attendance.setClassAttendance(gymClass);
        }

        attendanceRepository.save(attendance);

        return Response.builder()
                .status(200)
                .message("Attendance updated successfully")
                .build();
    }

    @Override
    public Response deleteAttendance(Long id) {
        Attendance attendance = attendanceRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Attendance not found"));
        attendanceRepository.delete(attendance);
        return Response.builder()
                .status(200)
                .message("Attendance deleted successfully")
                .build();
    }
}
