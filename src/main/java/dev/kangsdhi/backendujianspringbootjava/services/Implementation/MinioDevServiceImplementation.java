package dev.kangsdhi.backendujianspringbootjava.services.Implementation;

import dev.kangsdhi.backendujianspringbootjava.services.MinioDevService;
import io.minio.*;
import io.minio.errors.MinioException;
import io.minio.http.Method;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

@Service
public class MinioDevServiceImplementation implements MinioDevService {

    @Autowired
    private MinioClient minioClient;

    private final String bucketName = "ujian-bucket";

    @Override
    public String testConnection() {
        try {
            boolean isBucketExist = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
            return isBucketExist ? "Bucket exists" : "Bucket not exists";
        } catch (MinioException | InvalidKeyException | NoSuchAlgorithmException | IOException e) {
            return "Error : " + e.getMessage();
        }
    }

    @Override
    public String uploadFile(MultipartFile file) throws Exception {

        String uuid = UUID.randomUUID().toString();
        String originalFilename = file.getOriginalFilename();

        String extension = "";
        if (originalFilename != null && originalFilename.contains(".")){
            extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        }

        String newFileName = uuid + extension;

        if (!minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build())){
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
        }

        minioClient.putObject(
                PutObjectArgs.builder()
                        .bucket(bucketName)
                        .object("pertanyaan/" + newFileName)
                        .stream(file.getInputStream(), file.getSize(), -1)
                        .contentType(file.getContentType())
                        .build()
        );

        return newFileName;
    }

    @Override
    public String getFileUrl(String fileName) throws Exception {
        return minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder()
                        .bucket(bucketName)
                        .object("pertanyaan/" + fileName)
                        .method(Method.GET)
                        .expiry(60)
                .build());
    }

    @Override
    public Resource downloadFile(String fileName) throws Exception {
        InputStream stream = minioClient.getObject(
                GetObjectArgs.builder()
                        .bucket(bucketName)
                        .object("pertanyaan/" + fileName)
                        .build()
        );
        return new InputStreamResource(stream);
    }
}
