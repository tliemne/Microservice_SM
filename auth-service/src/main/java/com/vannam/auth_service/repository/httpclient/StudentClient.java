package com.vannam.auth_service.repository.httpclient;

import com.vannam.auth_service.dto.request.SchoolManagerRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "student-service", url = "http://localhost:8081/student-service")
public interface StudentClient {

    @PostMapping(value = "/internal/school-managers",produces = MediaType.APPLICATION_JSON_VALUE)
    void createSchoolManager(@RequestBody SchoolManagerRequest request);
}
