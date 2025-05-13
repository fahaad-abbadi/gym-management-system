package com.example.advanced_mappings.service;

import com.example.advanced_mappings.dtos.ClassDTO;
import com.example.advanced_mappings.dtos.Response;

public interface ClassService {
    Response createClass(ClassDTO classDTO);
    Response getAllClasses();
    Response getClassById(Long id);
    Response updateClass(Long id, ClassDTO classDTO);
    Response deleteClass(Long id);
}
