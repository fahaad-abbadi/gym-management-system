package com.example.advanced_mappings.service.impl;

import com.example.advanced_mappings.dtos.ClassEnrollmentDTO;
import com.example.advanced_mappings.dtos.Response;
import com.example.advanced_mappings.exceptions.NotFoundException;
import com.example.advanced_mappings.models.ClassEnrollment;
import com.example.advanced_mappings.models.User;
import com.example.advanced_mappings.repos.ClassEnrollmentRepository;
import com.example.advanced_mappings.repos.ClassRepository;
import com.example.advanced_mappings.repos.UserRepository;
import com.example.advanced_mappings.service.ClassEnrollmentService;
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
public class ClassEnrollmentServiceImpl implements ClassEnrollmentService {

    private final ClassEnrollmentRepository classEnrollmentRepository;
    private final UserRepository userRepository;
    private final ClassRepository classRepository;
    private final ModelMapper modelMapper;

    @Override
    public Response createClassEnrollment(ClassEnrollmentDTO dto) {
        // Fetch user (member)
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new NotFoundException("Member user not found"));

        // Fetch class
        Class gymClass = classRepository.findById(dto.getClassId())
                .orElseThrow(() -> new NotFoundException("Class not found"));

        // Map DTO to entity
        ClassEnrollment enrollment = modelMapper.map(dto, ClassEnrollment.class);

        // Link both user and class explicitly
        enrollment.setMemberClassEnrollment(user);
        enrollment.setClasses(gymClass);

        // Optional: use user.addClassEnrollment(enrollment);
        user.addClassEnrollment(enrollment);

        classEnrollmentRepository.save(enrollment);

        return Response.builder()
                .status(200)
                .message("Class Enrollment saved and linked to user")
                .build();
    }

    @Override
    public Response getAllClassEnrollments() {
        List<ClassEnrollment> all = classEnrollmentRepository.findAll(Sort.by(Sort.Direction.DESC, "enrollmentId"));
        List<ClassEnrollmentDTO> dtoList = modelMapper.map(all, new TypeToken<List<ClassEnrollmentDTO>>() {}.getType());
        return Response.builder().status(200).message("success").classEnrollments(dtoList).build();
    }

    @Override
    public Response getClassEnrollmentById(Long id) {
        ClassEnrollment enrollment = classEnrollmentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Class Enrollment not found"));
        ClassEnrollmentDTO dto = modelMapper.map(enrollment, ClassEnrollmentDTO.class);
        return Response.builder().status(200).message("success").classEnrollment(dto).build();
    }

    @Override
    public Response updateClassEnrollment(Long id, ClassEnrollmentDTO dto) {
        ClassEnrollment enrollment = classEnrollmentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Class Enrollment not found"));

        modelMapper.map(dto, enrollment);

        if (dto.getUserId() != null) {
            User user = userRepository.findById(dto.getUserId())
                    .orElseThrow(() -> new NotFoundException("Member user not found"));
            enrollment.setMemberClassEnrollment(user);
        }

        if (dto.getClassId() != null) {
            Class gymClass = classRepository.findById(dto.getClassId())
                    .orElseThrow(() -> new NotFoundException("Class not found"));
            enrollment.setClasses(gymClass);
        }

        classEnrollmentRepository.save(enrollment);

        return Response.builder()
                .status(200)
                .message("Class Enrollment updated successfully")
                .build();
    }

    @Override
    public Response deleteClassEnrollment(Long id) {
        ClassEnrollment enrollment = classEnrollmentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Class Enrollment not found"));
        classEnrollmentRepository.delete(enrollment);
        return Response.builder().status(200).message("Class Enrollment deleted").build();
    }
}
