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
public class StudentService {
    ClassesRepository classesRepository;
    StudentMapper studentMapper;
    SchoolRepository schoolRepository;
    StudentRepository studentRepository;

    public List<StudentResponse> getAll(){
        return studentRepository.findAll().stream()
                .map(studentMapper::toResponse)
                .collect(Collectors.toList());
    }

    public StudentResponse create(StudentRequest request){
        Student student = studentRepository.findByCodeAndDeletedFalse(request.getCode())
                .orElseThrow(() -> new AppException(ErrorCode.STUDENT_EXISTED));

        Classes classes = classesRepository.findByCodeAndDeletedFalse(request.getClassCode())
                .orElseThrow(() -> new AppException(ErrorCode.CLASSES_EXISTED));

        School school = schoolRepository.findByCodeAndDeletedFalse(request.getSchoolCode())
                        .orElseThrow(() -> new AppException(ErrorCode.SCHOOL_NOT_EXISTED));

        student.setSchool(school);
        student.setClazz(classes);
        studentMapper.toEntity(request);
        return studentMapper.toResponse(studentRepository.save(student));
    }

    public StudentResponse getByCode(String code){
        Student student = studentRepository.findByCodeAndDeletedFalse(code)
                .orElseThrow(() -> new AppException(ErrorCode.CLASSES_NOT_EXISTED));
        return studentMapper.toResponse(student);
    }

    public StudentResponse update(String code, StudentRequest request){
        Student student = studentRepository.findByCodeAndDeletedFalse(code)
                .orElseThrow(() -> new AppException(ErrorCode.STUDENT_NOT_EXISTED));
        if (request.getCode() != null && !code.equals(request.getCode())) {
            if (studentRepository.existsByCodeAndDeletedFalse(request.getCode())) {
                throw new AppException(ErrorCode.STUDENT_EXISTED);
            }
        }
        if(request.getSchoolCode() != null && !request.getSchoolCode().isBlank()){
            School school = schoolRepository.findByCodeAndDeletedFalse(request.getSchoolCode())
                    .orElseThrow(() -> new AppException(ErrorCode.SCHOOL_NOT_EXISTED));
            student.setSchool(school);
        }
        if(request.getClassCode() != null && !request.getClassCode().isBlank()){
            Classes classes = classesRepository.findByCodeAndDeletedFalse(request.getClassCode())
                    .orElseThrow(() -> new AppException(ErrorCode.CLASSES_NOT_EXISTED));
            student.setClazz(classes);
        }


        studentMapper.updateEntity(request, student);

        return studentMapper.toResponse(studentRepository.save(student));
    }

    public StudentResponse delete(String code){
        Student student = studentRepository.findByCodeAndDeletedFalse(code)
                .orElseThrow(() -> new AppException(ErrorCode.CLASSES_NOT_EXISTED));

        student.setDeleted(true);
        return studentMapper.toResponse(studentRepository.save(student));
    }

    public StudentResponse restore(String code) {
        Student student = studentRepository.findByCodeAndDeletedTrue(code)
                .orElseThrow(() -> new AppException(ErrorCode.CLASSES_NOT_EXISTED));

        student.setDeleted(false);
        return studentMapper.toResponse(studentRepository.save(student));
    }
}
