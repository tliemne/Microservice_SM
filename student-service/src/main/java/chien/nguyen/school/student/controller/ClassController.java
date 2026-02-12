package chien.nguyen.school.student.controller;

import chien.nguyen.school.student.dtos.request.ClassRequest;
import chien.nguyen.school.student.dtos.response.ClassResponse;
import chien.nguyen.school.student.service.ClassService;
import com.school.common_library.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/classes")
@RequiredArgsConstructor
public class ClassController {

    private final ClassService classService;

//    @PreAuthorize("hasAuthority('CLASS_VIEW')")
    @GetMapping
    public ResponseEntity<ApiResponse<List<ClassResponse>>> list() {
        return ResponseEntity.ok(
                ApiResponse.success(classService.getClasses())
        );
    }

//    @PreAuthorize("hasAuthority('CLASS_VIEW')")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ClassResponse>> get(@PathVariable Long id) {
        return ResponseEntity.ok(
                ApiResponse.success(classService.getClassById(id))
        );
    }

//    @PreAuthorize("hasAuthority('CLASS_CREATE')")
    @PostMapping
    public ResponseEntity<ApiResponse<ClassResponse>> create(
            @Valid @RequestBody ClassRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(classService.createClass(request)));
    }

//    @PreAuthorize("hasAuthority('CLASS_UPDATE')")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ClassResponse>> update(
            @PathVariable Long id,
            @Valid @RequestBody ClassRequest request) {

        return ResponseEntity.ok(
                ApiResponse.success(classService.updateClass(id, request))
        );
    }

//    @PreAuthorize("hasAuthority('CLASS_DELETE')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {

        classService.deleteClass(id);
        return ResponseEntity.ok(ApiResponse.success());
    }
}
