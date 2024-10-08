package com.devkk.micro.config;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

/**
 * @author zhongkunming
 */
@Data
@Validated
@Configuration
@ConfigurationProperties(prefix = "application.jwt")
public class JwtConfig {

    @NotBlank
    private String key;
}
