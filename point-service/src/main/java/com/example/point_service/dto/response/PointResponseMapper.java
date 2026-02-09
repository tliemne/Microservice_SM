package com.example.point_service.dto.response;

import com.school.common_library.security.UserContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PointResponseMapper {

    public Object map(PointResponse point) {
        UserContext ctx = UserContext.getCurrentUser();
        if (ctx != null && ctx.isStudent()) {

            return PointResponseForStudent.builder()
                    .subject(point.getSubject())
                    .score(point.getScore())
                    .semester(point.getSemester())
                    .build();
        }
        return point;
    }
}
