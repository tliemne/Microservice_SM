package com.school.student_service.service;

import com.school.common_library.exception.*;
import com.school.student_service.dto.request.*;
import com.school.student_service.dto.response.*;
import com.school.student_service.mapper.*;
import com.school.student_service.model.*;
import com.school.student_service.repository.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ClassesService {
    ClassesRepository classesRepository;
    ClassesMapper classesMapper;
    SchoolRepository schoolRepository;

    public List<ClassesResponse> getAll(){
        return classesRepository.findAll().stream()
                .map(classesMapper::toResponse)
                .collect(Collectors.toList());
    }

    public ClassesResponse create(ClassesRequest request){

        if (classesRepository.existsByCodeAndDeletedFalse(request.getCode())) {
            throw new AppException(ErrorCode.CLASSES_EXISTED);
        }

        School school = schoolRepository.findByCodeAndDeletedFalse(request.getSchoolCode())
                .orElseThrow(() -> new AppException(ErrorCode.SCHOOL_NOT_EXISTED));
        Classes classes = classesMapper.toEntity(request);
        classes.setSchool(school);
        return classesMapper.toResponse(classesRepository.save(classes));
    }

    public ClassesResponse getByCode(String code){
        Classes classes = classesRepository.findByCodeAndDeletedFalse(code)
                .orElseThrow(() -> new AppException(ErrorCode.SCHOOL_NOT_EXISTED));
        return classesMapper.toResponse(classes);
    }

    public ClassesResponse update(String code, ClassesRequest request){
        Classes classes = classesRepository.findByCodeAndDeletedFalse(code)
                .orElseThrow(() -> new AppException(ErrorCode.CLASSES_NOT_EXISTED));

        if (request.getCode() != null && !code.equals(request.getCode())) {
            if (classesRepository.existsByCodeAndDeletedFalse(request.getCode())) {
                throw new AppException(ErrorCode.CLASSES_EXISTED);
            }
        }

        if (request.getSchoolCode() != null && !request.getSchoolCode().isBlank()) {
            School school = schoolRepository.findByCodeAndDeletedFalse(request.getSchoolCode())
                    .orElseThrow(() -> new AppException(ErrorCode.SCHOOL_NOT_EXISTED));
            classes.setSchool(school);
        }
        
        classesMapper.updateEntity(request, classes);

        return classesMapper.toResponse(classesRepository.save(classes));
    }

    public ClassesResponse delete(String code){
        Classes classes = classesRepository.findByCodeAndDeletedFalse(code)
                .orElseThrow(() -> new AppException(ErrorCode.CLASSES_NOT_EXISTED));

        classes.setDeleted(true);
        return classesMapper.toResponse(classesRepository.save(classes));
    }

    public ClassesResponse restore(String code){
        Classes classes = classesRepository.findByCodeAndDeletedTrue(code)
                .orElseThrow(() -> new AppException(ErrorCode.CLASSES_NOT_EXISTED));

        classes.setDeleted(false);
        return classesMapper.toResponse(classesRepository.save(classes));
    }


}
