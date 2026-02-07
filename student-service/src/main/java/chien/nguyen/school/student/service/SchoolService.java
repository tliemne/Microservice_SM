package chien.nguyen.school.student.service;

import org.springframework.security.core.Authentication;
import chien.nguyen.school.student.dtos.request.SchoolRequest;
import chien.nguyen.school.student.dtos.response.SchoolResponse;

import java.util.List;

public interface SchoolService {

    List<SchoolResponse> getSchools(Authentication authentication);

    SchoolResponse getSchoolById(Authentication authentication, Long id);

    SchoolResponse createSchool(Authentication authentication, SchoolRequest request);

    SchoolResponse updateSchool(Authentication authentication, Long id, SchoolRequest request);

    void deleteSchool(Authentication authentication, Long id);
}
