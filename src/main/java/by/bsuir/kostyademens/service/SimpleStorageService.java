package by.bsuir.kostyademens.service;


import by.bsuir.kostyademens.exception.BucketInitializationException;
import by.bsuir.kostyademens.exception.MinioOperationException;
import io.minio.*;
import io.minio.messages.Item;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

@Service
@RequiredArgsConstructor
public class SimpleStorageService {

    private final MinioClient minioClient;

    @Value("${minio.bucket.name}")
    private String bucketName;


    @PostConstruct
    private void init() {
        try {
            boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
            if (!found) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
            }
        } catch (Exception e) {
            throw new BucketInitializationException("Failed to initialize bucket: " + bucketName);
        }
    }


    public void createUserFolder(Long id) {
        try {
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object("user-" + id + "-files/").stream(
                                    new ByteArrayInputStream(new byte[]{}), 0, -1)
                            .build());
        } catch (Exception e) {
            throw new MinioOperationException("Failed to create user folder");
        }
    }


    public void uploadFile(MultipartFile file, String key) {
        try {

            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(key + file.getOriginalFilename())
                            .contentType(file.getContentType())
                            .stream(file.getInputStream(), file.getSize(), -1)
                            .build()
            );
        } catch (Exception e) {
            throw new MinioOperationException("Failed to upload file to MinIO bucket");
        }
    }


    public void uploadEmptyFolder(String folderName) {
        try {

            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(folderName)
                            .stream(new ByteArrayInputStream(new byte[]{}), 0, -1)
                            .build()
            );
        } catch (Exception e) {
            throw new MinioOperationException("Failed to upload folder to MinIO bucket");
        }
    }


    public InputStream getFile(String objectName) {
        try {

            return minioClient.getObject(
                    GetObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectName)
                            .build()
            );
        } catch (Exception e) {
            throw new MinioOperationException("Failed to retrieve file from MinIO bucket");
        }
    }


    public void deleteFile(String objectName) {
        try {

            minioClient.removeObject(
                    RemoveObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectName)
                            .build()
            );
        } catch (Exception e) {
            throw new MinioOperationException("Failed to delete file from MinIO bucket");
        }
    }


    public void renameFile(String newName, String oldName) {
        try {

            minioClient.copyObject(
                    CopyObjectArgs.builder()
                            .bucket(bucketName)
                            .object(newName)
                            .source(
                                    CopySource.builder()
                                            .bucket(bucketName)
                                            .object(oldName)
                                            .build())
                            .build());

            deleteFile(oldName);
        } catch (Exception e) {
            throw new MinioOperationException("Failed to rename file");
        }
    }


    public Iterable<Result<Item>> getAllFiles(String prefix, boolean isRecursive) {
        return minioClient.listObjects(
                ListObjectsArgs.builder()
                        .bucket(bucketName)
                        .recursive(isRecursive)
                        .prefix(prefix)
                        .build()
        );
    }
}
