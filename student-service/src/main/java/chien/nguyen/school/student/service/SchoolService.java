package chien.nguyen.school.student.service;

import chien.nguyen.school.student.dtos.request.SchoolRequest;
import chien.nguyen.school.student.dtos.response.SchoolResponse;

import java.util.List;

public interface SchoolService {

    List<SchoolResponse> getSchools();

    SchoolResponse getSchoolById(Long id);

    SchoolResponse createSchool(SchoolRequest request);

    SchoolResponse updateSchool(Long id, SchoolRequest request);

    void deleteSchool(Long id);
}
