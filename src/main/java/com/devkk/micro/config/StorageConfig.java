package com.devkk.micro.config;

import com.devkk.micro.framework.storage.LocalStorageService;
import com.devkk.micro.framework.storage.MinioStorageService;
import com.devkk.micro.framework.storage.StorageService;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

/**
 * @author zhongkunming
 */
@Data
@Validated
@Configuration
@ConfigurationProperties(prefix = StorageConfig.CONFIG_PREFIX)
public class StorageConfig {

    public final static String CONFIG_PREFIX = "application.storage";

    @NotNull
    private StorageConfig.Type type;

    @NotNull
    private Boolean direct;

    private String basePath;

    private String bucket;

    private String url;

    private String ak;

    private String sk;

    @Bean
    @ConditionalOnProperty(prefix = CONFIG_PREFIX, name = "type", havingValue = "minio")
    public StorageService minioStorage() {
        return new MinioStorageService(url, ak, sk, bucket);
    }

    @Bean
    @ConditionalOnProperty(prefix = CONFIG_PREFIX, name = "type", havingValue = "local")
    public StorageService localStorage() {
        return new LocalStorageService(basePath);
    }

    enum Type {
        MINIO,
        LOCAL,
    }
}
