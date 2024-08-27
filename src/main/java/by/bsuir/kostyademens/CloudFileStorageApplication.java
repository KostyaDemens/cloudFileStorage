package by.bsuir.kostyademens;

import by.bsuir.kostyademens.model.BreadCrumb;
import io.minio.ListObjectsArgs;
import io.minio.MinioClient;
import io.minio.RemoveObjectArgs;
import io.minio.Result;
import io.minio.errors.MinioException;
import io.minio.messages.Item;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@SpringBootApplication
public class CloudFileStorageApplication {

	@SneakyThrows
	public static void main(String[] args) {
		SpringApplication.run(CloudFileStorageApplication.class, args);
	}
	}


