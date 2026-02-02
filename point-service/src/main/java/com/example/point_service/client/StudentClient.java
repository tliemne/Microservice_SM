package com.example.point_service.client;

import com.example.point_service.dto.ApiResponse;
import com.example.point_service.dto.response.StudentResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
@Slf4j
public class StudentClient {

    private final RestTemplate restTemplate;

    @Value("${student-service.url:http://student-service:8090}")
    private String studentServiceUrl;

    public StudentResponse getStudent(Long studentId, String authToken) {
        try {
            String url = studentServiceUrl + "/students/" + studentId;

            org.springframework.http.HttpEntity<Void> request = new org.springframework.http.HttpEntity<>(
                    createHeaders(authToken)
            );

            // Use ParameterizedTypeReference to handle ApiResponse<StudentResponse>
            org.springframework.http.ResponseEntity<ApiResponse<StudentResponse>> response =
                restTemplate.exchange(url, org.springframework.http.HttpMethod.GET, request,
                    new ParameterizedTypeReference<ApiResponse<StudentResponse>>(){});

            if (response.getBody() != null && response.getBody().getData() != null) {
                return response.getBody().getData();
            }
            return null;
        } catch (Exception e) {
            log.error("Failed to fetch student {}:", studentId, e);
            return null;
        }
    }

    private HttpHeaders createHeaders(String authToken) {
        HttpHeaders headers = new HttpHeaders();
        if (authToken != null && !authToken.isEmpty()) {
            headers.set("Authorization", authToken);
        }
        headers.set("Content-Type", "application/json");
        return headers;
    }
}
