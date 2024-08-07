package by.bsuir.kostyademens.service;

import io.minio.MinioClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SimpleStorageService {

    private final MinioClient minioClient;
}
