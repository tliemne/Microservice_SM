package chien.nguyen.school.student.controller;

import chien.nguyen.school.student.dtos.request.SchoolRequest;
import chien.nguyen.school.student.dtos.response.SchoolResponse;
import chien.nguyen.school.student.service.SchoolService;
import com.school.common_library.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/schools")
@RequiredArgsConstructor
public class SchoolController {

    private final SchoolService schoolService;

//    @PreAuthorize("hasAuthority('SCHOOL_VIEW')")
    @GetMapping
    public ResponseEntity<ApiResponse<List<SchoolResponse>>> list() {
        return ResponseEntity.ok(
                ApiResponse.success(schoolService.getSchools())
        );
    }

//    @PreAuthorize("hasAuthority('SCHOOL_VIEW')")
    @GetMapping("/{id}")
    @PreAuthorize("@auth.isSchoolManager(#id.toString())")
    public ResponseEntity<ApiResponse<SchoolResponse>> get(@PathVariable Long id) {
        return ResponseEntity.ok(
                ApiResponse.success(schoolService.getSchoolById(id))
        );
    }

//    @PreAuthorize("hasAuthority('SCHOOL_CREATE')")
    @PostMapping
    public ResponseEntity<ApiResponse<SchoolResponse>> create(
            @Valid @RequestBody SchoolRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(schoolService.createSchool(request)));
    }
//
//    @PreAuthorize("hasAuthority('SCHOOL_UPDATE')")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<SchoolResponse>> update(
            @PathVariable Long id,
            @Valid @RequestBody SchoolRequest request
    ) {
        return ResponseEntity.ok(
                ApiResponse.success(schoolService.updateSchool(id, request))
        );
    }

//    @PreAuthorize("hasAuthority('SCHOOL_DELETE')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        schoolService.deleteSchool(id);
        return ResponseEntity.ok(ApiResponse.success());
    }
}
