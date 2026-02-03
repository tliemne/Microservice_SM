package com.school.student_service.service;

import com.school.common_library.exception.*;
import com.school.student_service.dto.request.SchoolRequest;
import com.school.student_service.dto.response.SchoolResponse;
import com.school.student_service.mapper.SchoolMapper;
import com.school.student_service.model.School;
import com.school.student_service.repository.SchoolRepository;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SchoolService {
    SchoolRepository schoolRepository;
    SchoolMapper schoolMapper;

    public List<SchoolResponse> getAll(){
        return schoolRepository.findAll().stream()
                .map(schoolMapper::toResponse)
                .collect(Collectors.toList());
    }

    public SchoolResponse create(SchoolRequest request){
       if(schoolRepository.existsByCodeAndDeletedFalse(request.getCode()))
                throw new AppException(ErrorCode.SCHOOL_EXISTED);

       School school = schoolMapper.toEntity(request);
       return schoolMapper.toResponse(schoolRepository.save(school));
    }

    public SchoolResponse getByCode(String code){
        School school = schoolRepository.findByCodeAndDeletedFalse(code)
                .orElseThrow(() -> new AppException(ErrorCode.SCHOOL_EXISTED));
        return schoolMapper.toResponse(school);
    }

    public SchoolResponse update(String code, SchoolRequest request){
        School school = schoolRepository.findByCodeAndDeletedFalse(code)
                .orElseThrow(() -> new AppException(ErrorCode.SCHOOL_NOT_EXISTED));

        schoolMapper.updateEntity(request, school);

        return schoolMapper.toResponse(schoolRepository.save(school));
    }

    public SchoolResponse delete(String code){
        School school = schoolRepository.findByCodeAndDeletedFalse(code)
                .orElseThrow(() -> new AppException(ErrorCode.SCHOOL_NOT_EXISTED));

        school.setDeleted(true);
        return schoolMapper.toResponse(schoolRepository.save(school));
    }

    public SchoolResponse restore(String code){
        School school = schoolRepository.findByCodeAndDeletedTrue(code)
                .orElseThrow(() -> new AppException(ErrorCode.SCHOOL_NOT_EXISTED));

        school.setDeleted(false);
        return schoolMapper.toResponse(schoolRepository.save(school));
    }


}
