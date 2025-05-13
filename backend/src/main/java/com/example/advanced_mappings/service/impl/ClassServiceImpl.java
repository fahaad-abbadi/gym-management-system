package com.example.advanced_mappings.service.impl;

import com.example.advanced_mappings.dtos.ClassDTO;
import com.example.advanced_mappings.dtos.Response;
import com.example.advanced_mappings.exceptions.NotFoundException;
import com.example.advanced_mappings.models.Trainer;
import com.example.advanced_mappings.repos.ClassRepository;
import com.example.advanced_mappings.repos.TrainerRepository;
import com.example.advanced_mappings.service.ClassService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.example.advanced_mappings.models.Class;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class ClassServiceImpl implements ClassService {

    private final ClassRepository classRepository;
    private final TrainerRepository trainerRepository;
    private final ModelMapper modelMapper;

    @Override
    public Response createClass(ClassDTO classDTO) {
        // Map basic fields
        Class clazz = modelMapper.map(classDTO, Class.class);

        // Fetch and set Trainer entity explicitly if trainerId is provided
        if (classDTO.getTrainerId() != null) {
            Trainer trainer = trainerRepository.findById(classDTO.getTrainerId())
                    .orElseThrow(() -> new NotFoundException("Trainer not found"));
            clazz.setTrainerClass(trainer);
        }

        classRepository.save(clazz);

        return Response.builder()
                .status(200)
                .message("Class saved")
                .build();
    }

    @Override
    public Response getAllClasses() {
        List<Class> classes = classRepository.findAll(Sort.by(Sort.Direction.DESC, "classId"));

        List<ClassDTO> dtoList = classes.stream().map(clazz -> {
            ClassDTO dto = modelMapper.map(clazz, ClassDTO.class);

            if (clazz.getTrainerClass() != null) {
                dto.setTrainerId(clazz.getTrainerClass().getTrainerId());
            }

            return dto;
        }).toList();

        return Response.builder()
                .status(200)
                .message("success")
                .classes(dtoList)
                .build();
    }

    @Override
    public Response getClassById(Long id) {
        Class clazz = classRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Class not found"));

        ClassDTO dto = modelMapper.map(clazz, ClassDTO.class);

        if (clazz.getTrainerClass() != null) {
            dto.setTrainerId(clazz.getTrainerClass().getTrainerId());
        }

        return Response.builder()
                .status(200)
                .message("success")
                .clazz(dto)
                .build();
    }

    @Override
    public Response updateClass(Long id, ClassDTO dto) {
        Class clazz = classRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Class not found"));

        // Map basic fields
        modelMapper.map(dto, clazz);

        // Update Trainer if provided
        if (dto.getTrainerId() != null) {
            Trainer trainer = trainerRepository.findById(dto.getTrainerId())
                    .orElseThrow(() -> new NotFoundException("Trainer not found"));
            clazz.setTrainerClass(trainer);
        }

        classRepository.save(clazz);

        return Response.builder()
                .status(200)
                .message("Class updated")
                .build();
    }

    @Override
    public Response deleteClass(Long id) {
        Class clazz = classRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Class not found"));

        classRepository.delete(clazz);

        return Response.builder()
                .status(200)
                .message("Class deleted")
                .build();
    }
}
