package com.devkk.micro.framework.storage;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * @author zhongkunming
 */
@Slf4j
@Getter(AccessLevel.PRIVATE)
public class LocalStorageService implements StorageService {

    private final String basePath;

    public LocalStorageService(String basePath) {
        Path path = Path.of(basePath);
        basePath = path.getFileName().toAbsolutePath().toString();
        if (Files.notExists(path)) {
            if (log.isDebugEnabled()) {
                log.debug("application will create directory in {}", basePath);
            }
            try {
                Files.createDirectory(path);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        if (!Files.isDirectory(path)) {
            throw new RuntimeException("the path is not a directory: " + basePath);
        }
        this.basePath = basePath;
    }

    @Override
    public void store(String path, InputStream is, Integer size) throws Exception {
        Path filePath = Path.of(basePath, path);
        try (OutputStream os = Files.newOutputStream(filePath)) {
            IOUtils.copy(is, os);
        }
    }

    @Override
    public InputStream read(String path) throws Exception {
        Path filePath = Path.of(basePath, path);
        return Files.newInputStream(filePath);
    }

    @Override
    public boolean exist(String path) throws Exception {
        Path filePath = Path.of(basePath, path);
        return Files.exists(filePath);
    }

    @Override
    public void delete(String path) throws Exception {
        Path filePath = Path.of(basePath, path);
        Files.deleteIfExists(filePath);
    }
}
