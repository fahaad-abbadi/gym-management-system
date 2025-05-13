package com.example.advanced_mappings.service;

import com.example.advanced_mappings.dtos.ClassEnrollmentDTO;
import com.example.advanced_mappings.dtos.Response;

public interface ClassEnrollmentService {

    Response createClassEnrollment(ClassEnrollmentDTO classEnrollmentDTO);

    Response getAllClassEnrollments();

    Response getClassEnrollmentById(Long id);

    Response updateClassEnrollment(Long id, ClassEnrollmentDTO classEnrollmentDTO);

    Response deleteClassEnrollment(Long id);
}
