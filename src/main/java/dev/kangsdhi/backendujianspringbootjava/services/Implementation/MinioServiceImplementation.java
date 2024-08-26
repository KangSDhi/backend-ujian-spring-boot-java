package dev.kangsdhi.backendujianspringbootjava.services.Implementation;

import dev.kangsdhi.backendujianspringbootjava.dto.response.ResponseWithMessageAndData;
import dev.kangsdhi.backendujianspringbootjava.services.MinioService;
import io.minio.*;
import io.minio.errors.*;
import io.minio.http.Method;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class MinioServiceImplementation implements MinioService {

    @Autowired
    private MinioClient minioClient;

    private final String bucketName = "ujian-bucket";

    @Override
    public ResponseWithMessageAndData<Map<String, String>> uploadGambarPertanyaan(MultipartFile file) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        String randomUUID = UUID.randomUUID().toString();
        String originalFilename = file.getOriginalFilename();

        String extension = "";
        if (originalFilename != null && originalFilename.contains(".")) {
            extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        }

        String newFilename = randomUUID + extension;

        if (!minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build())){
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
        }

        minioClient.putObject(
                PutObjectArgs.builder()
                        .bucket(bucketName)
                        .object("pertanyaan/" + newFilename)
                        .stream(file.getInputStream(), file.getSize(), -1)
                        .contentType(file.getContentType())
                        .build()
        );

        ResponseWithMessageAndData<Map<String, String>> responseWithMessageAndData = new ResponseWithMessageAndData<>();
        responseWithMessageAndData.setHttpCode(HttpStatus.CREATED.value());
        responseWithMessageAndData.setMessage("Upload Gambar Pertanyaan successful");
        responseWithMessageAndData.setData(Map.of("gambar_pertanyaan", newFilename));

        return responseWithMessageAndData;
    }

    @Override
    public ResponseWithMessageAndData<Map<String, String>> getUrlGambarPertanyaan(String gambarPertanyaan) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {

        ResponseWithMessageAndData<Map<String, String>> response = new ResponseWithMessageAndData<>();

        try {
            minioClient.statObject(
                    StatObjectArgs.builder()
                            .bucket(bucketName)
                            .object("pertanyaan/" + gambarPertanyaan)
                            .build()
            );

            String urlImage = minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder()
                    .bucket(bucketName)
                    .object("pertanyaan/" + gambarPertanyaan)
                    .method(Method.GET)
                    .expiry(60)
                    .build());
            Map<String, String> data = new HashMap<>();
            data.put("url", urlImage);

            response.setHttpCode(HttpStatus.OK.value());
            response.setMessage("Berhasil Mengambil URL Gambar Pertanyaan");
            response.setData(data);
        } catch (MinioException exception) {
            if (exception.getMessage().contains("Object does not exist")){
                response.setHttpCode(HttpStatus.NOT_FOUND.value());
                response.setMessage("Gambar Pertanyaan Tidak Ditemukan!");
            } else {
                response.setHttpCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
                response.setMessage(exception.getMessage());
            }
        }

        return response;
    }

    @Override
    public ResponseWithMessageAndData<Map<String, String>> uploadGambarJawaban(MultipartFile file) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        String randomUUID = UUID.randomUUID().toString();
        String originalFilename = file.getOriginalFilename();

        String extension = "";
        if (originalFilename != null && originalFilename.contains(".")) {
            extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        }

        String newFilename = randomUUID + extension;

        if (!minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build())){
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
        }

        minioClient.putObject(
                PutObjectArgs.builder()
                        .bucket(bucketName)
                        .object("jawaban/" + newFilename)
                        .stream(file.getInputStream(), file.getSize(), -1)
                        .contentType(file.getContentType())
                        .build()
        );

        ResponseWithMessageAndData<Map<String, String>> responseWithMessageAndData = new ResponseWithMessageAndData<>();
        responseWithMessageAndData.setHttpCode(HttpStatus.CREATED.value());
        responseWithMessageAndData.setMessage("Upload Gambar Jawaban successful");
        responseWithMessageAndData.setData(Map.of("gambar_jawaban", newFilename));

        return responseWithMessageAndData;
    }

    @Override
    public ResponseWithMessageAndData<Map<String, String>> getUrlGambarJawaban(String gambarJawaban) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        ResponseWithMessageAndData<Map<String, String>> response = new ResponseWithMessageAndData<>();

        try {
            minioClient.statObject(
                    StatObjectArgs.builder()
                            .bucket(bucketName)
                            .object("jawaban/" + gambarJawaban)
                            .build()
            );

            String urlImage = minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder()
                    .bucket(bucketName)
                    .object("jawaban/" + gambarJawaban)
                    .method(Method.GET)
                    .expiry(60)
                    .build());
            Map<String, String> data = new HashMap<>();
            data.put("url", urlImage);

            response.setHttpCode(HttpStatus.OK.value());
            response.setMessage("Berhasil Mengambil URL Gambar Jawaban");
            response.setData(data);
        } catch (MinioException exception) {
            if (exception.getMessage().contains("Object does not exist")){
                response.setHttpCode(HttpStatus.NOT_FOUND.value());
                response.setMessage("Gambar Jawaban Tidak Ditemukan!");
            } else {
                response.setHttpCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
                response.setMessage(exception.getMessage());
            }
        }

        return response;
    }
}
