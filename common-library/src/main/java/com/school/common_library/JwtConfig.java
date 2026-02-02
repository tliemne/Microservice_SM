package com.school.common_library;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.*;

@Data
@Configuration
@ConfigurationProperties(prefix = "school.jwt")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class JwtConfig {
    String signerKey = "3de06d74dcee52b342cad1730c36b4ce32ad762b093e90b11617d8550bf79ea1";
    private long accessTokenValidityInSeconds = 900;
    private long refreshTokenValidityInSeconds = 86400;
}
