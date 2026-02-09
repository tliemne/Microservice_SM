package com.school.common_library.security;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.*;

@Data
@Configuration
@ConfigurationProperties(prefix = "school.jwt")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class JwtConfig {
    private String signerKey;
    private long accessTokenValidityInSeconds = 900;
    private long refreshTokenValidityInSeconds = 86400;
}
