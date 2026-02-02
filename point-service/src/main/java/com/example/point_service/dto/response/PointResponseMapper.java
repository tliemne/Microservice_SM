package com.example.point_service.dto.response;

import com.example.point_service.security.UserContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PointResponseMapper {

    public Object map(PointResponse point) {
        UserContext ctx = UserContext.getCurrentUser();
        if (ctx != null && ctx.isStudent()) {
            // hide student/internal ids for student role
            return PointResponseForStudent.builder()
                    .subject(point.getSubject())
                    .score(point.getScore())
                    .semester(point.getSemester())
                    .build();
        }
        // admin and school manager see full PointResponse
        return point;
    }
}
