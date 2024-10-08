package com.devkk.micro.framework.storage;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * @author zhongkunming
 */
public interface StorageService {

    default void store(String path, byte[] bytes) throws Exception {
        try (ByteArrayInputStream bais = new ByteArrayInputStream(bytes)) {
            store(path, bais, bytes.length);
        }
    }

    void store(String path, InputStream is, Integer size) throws Exception;

    InputStream read(String path) throws Exception;

    boolean exist(String path) throws Exception;

    void delete(String path) throws Exception;
}
