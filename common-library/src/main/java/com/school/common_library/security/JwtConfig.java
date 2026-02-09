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
    String signerKey = "bbzhMC+y7ZDMf5zT/BTmDWFrRakcUZL/9HtfaX7zj3uerFTCBMfstWnLExLJJJuQ";
    private long accessTokenValidityInSeconds = 900;
    private long refreshTokenValidityInSeconds = 86400;
}
