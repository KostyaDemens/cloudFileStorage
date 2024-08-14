package by.bsuir.kostyademens.service;

import by.bsuir.kostyademens.dto.ItemDto;
import com.fasterxml.jackson.databind.util.ArrayIterator;
import io.minio.*;
import io.minio.messages.Item;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Iterator;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class SimpleStorageService {

    private final MinioClient minioClient;

    @Value("${minio.bucket.name}")
    private String bucketName;

    @SneakyThrows
    @PostConstruct
    private void init() {
        boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
        if (!found) {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
        }
    }


    @SneakyThrows
    public void createUserFolder(Long id) {
        minioClient.putObject(
                PutObjectArgs.builder()
                        .bucket(bucketName)
                        .object("user-" + id + "-files/").stream(
                                new ByteArrayInputStream(new byte[]{}), 0, -1)
                        .build());
    }

    @SneakyThrows
    public void uploadFile(MultipartFile file, String key) {
        minioClient.putObject(
                PutObjectArgs.builder()
                        .bucket(bucketName)
                        .object(key + file.getOriginalFilename())
                        .contentType(file.getContentType())
                        .stream(file.getInputStream(), file.getSize(), -1)
                        .build()
        );
    }

    @SneakyThrows
    public void uploadFolder(String folderName) {
        minioClient.putObject(
                PutObjectArgs.builder()
                        .bucket(bucketName)
                        .object(folderName + "/")
                        .stream(new ByteArrayInputStream(new byte[]{}), 0, -1)
                        .build()
        );
    }

    @SneakyThrows
    public InputStream getFile(String objectName) {
        return minioClient.getObject(
                GetObjectArgs.builder()
                        .bucket(bucketName)
                        .object(objectName)
                        .build()
        );
    }

    @SneakyThrows
    public void deleteFile(String objectName) {
        minioClient.removeObject(
                RemoveObjectArgs.builder()
                .bucket(bucketName)
                .object(objectName)
                .build()
        );
    }

    @SneakyThrows
    public void renameFile(String newName, String oldName) {
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
    }


    @SneakyThrows
    public Iterable<Result<Item>> getAllFiles(String prefix) {
        return minioClient.listObjects(
                ListObjectsArgs.builder()
                        .bucket(bucketName)
                        .prefix(prefix)
                        .build()
        );
    }



}
