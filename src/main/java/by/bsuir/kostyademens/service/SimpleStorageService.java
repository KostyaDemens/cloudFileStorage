package by.bsuir.kostyademens.service;

import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.messages.Bucket;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SimpleStorageService {

    private final MinioClient minioClient;

    @Value("${minio.bucket.name}")
    private String bucketName;

    @SneakyThrows
    public List<Bucket> getAllBuckets() {
        List<Bucket> buckets = minioClient.listBuckets();
        for (Bucket bucket : buckets) {
            System.out.println(bucket.creationDate() + ", " + bucket.name());
        }
        return buckets;
    }

    @SneakyThrows
    public void createUserFolder(Long id) {
        minioClient.putObject(
                PutObjectArgs.builder()
                        .bucket(bucketName)
                        .object("user-" + id + "-files/").stream(
                                new ByteArrayInputStream(new byte[] {}), 0, -1)
                        .build());
    }
}
