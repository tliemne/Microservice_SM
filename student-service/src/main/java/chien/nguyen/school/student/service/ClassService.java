package chien.nguyen.school.student.service;

import org.springframework.security.core.Authentication;
import chien.nguyen.school.student.dtos.request.ClassRequest;
import chien.nguyen.school.student.dtos.response.ClassResponse;

import java.util.List;

public interface ClassService {

    List<ClassResponse> getClasses(Authentication authentication);

    ClassResponse getClassById(Authentication authentication, Long id);

    ClassResponse createClass(Authentication authentication, ClassRequest request);

    ClassResponse updateClass(Authentication authentication, Long id, ClassRequest request);

    void deleteClass(Authentication authentication, Long id);
}
