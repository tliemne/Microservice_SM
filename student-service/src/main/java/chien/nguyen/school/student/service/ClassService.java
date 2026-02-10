package chien.nguyen.school.student.service;

import chien.nguyen.school.student.dtos.request.ClassRequest;
import chien.nguyen.school.student.dtos.response.ClassResponse;

import java.util.List;

public interface ClassService {

    List<ClassResponse> getClasses();

    ClassResponse getClassById(Long id);

    ClassResponse createClass(ClassRequest request);

    ClassResponse updateClass(Long id, ClassRequest request);

    void deleteClass(Long id);
}
