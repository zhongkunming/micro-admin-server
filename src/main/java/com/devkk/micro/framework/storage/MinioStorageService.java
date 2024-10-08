package com.devkk.micro.framework.storage;

import io.minio.*;
import io.minio.http.Method;

import java.io.InputStream;
import java.util.concurrent.TimeUnit;

/**
 * @author zhongkunming
 */
public class MinioStorageService implements StorageService, Direct {

    private final String bucket;

    private final MinioClient minioClient;

    public MinioStorageService(String endpoint, String accessKey, String secretKey, String bucket) {
        this.bucket = bucket;
        this.minioClient = MinioClient.builder()
                .endpoint(endpoint)
                .credentials(accessKey, secretKey)
                .build();
    }

    @Override
    public void store(String path, InputStream is, Integer size) throws Exception {
        minioClient.putObject(
                PutObjectArgs.builder()
                        .bucket(bucket)
                        .object(path)
                        .stream(is, size, -1)
                        .build());
    }

    @Override
    public InputStream read(String path) throws Exception {
        return minioClient.getObject(
                GetObjectArgs.builder()
                        .bucket(bucket)
                        .object(path)
                        .build());
    }

    @Override
    public boolean exist(String path) throws Exception {
        minioClient.statObject(
                StatObjectArgs.builder()
                        .bucket(bucket)
                        .object(path)
                        .build());
        return true;
    }

    @Override
    public void delete(String path) throws Exception {
        minioClient.removeObject(
                RemoveObjectArgs.builder()
                        .bucket(bucket)
                        .object(path)
                        .build());
    }

    @Override
    public String directLink(String path) throws Exception {
        return minioClient.getPresignedObjectUrl(
                GetPresignedObjectUrlArgs.builder()
                        .bucket(bucket)
                        .object(path)
                        .method(Method.GET)
                        .expiry(1, TimeUnit.DAYS)
                        .build());
    }
}
