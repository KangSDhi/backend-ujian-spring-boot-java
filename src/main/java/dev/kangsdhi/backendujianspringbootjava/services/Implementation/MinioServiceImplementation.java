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
import java.util.Map;
import java.util.UUID;

@Service
public class MinioServiceImplementation implements MinioService {

    @Autowired
    private MinioClient minioClient;

    private final String bucketName = "ujian-bucket";

    @Override
    public ResponseWithMessageAndData<Map<String, String>> uploadGambarPertanyaan(MultipartFile file) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        String newFilename = generateFilename(file);
        uploadFile(file, "pertanyaan/" + newFilename);
        return buildResponse(HttpStatus.CREATED, "Upload Gambar Pertanyaan Sukses!", Map.of("gambar_pertanyaan", newFilename));
    }

    @Override
    public ResponseWithMessageAndData<Map<String, String>> getUrlGambarPertanyaan(String gambarPertanyaan) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        return generateUrlResponse("pertanyaan/" + gambarPertanyaan, "Berhasil Mengambil URL Gambar Pertanyaan", "Gambar Pertanyaan Tidak Ditemukan!");
    }

    @Override
    public ResponseWithMessageAndData<Map<String, String>> uploadGambarJawaban(MultipartFile file) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        String newFilename = generateFilename(file);
        uploadFile(file, "jawaban/" + newFilename);
        return buildResponse(HttpStatus.CREATED, "Upload Gambar Jawaban Sukses!", Map.of("jawaban", newFilename));
    }

    @Override
    public ResponseWithMessageAndData<Map<String, String>> getUrlGambarJawaban(String gambarJawaban) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        return generateUrlResponse("jawaban/" + gambarJawaban, "Berhasil Mengambil URL Gambar Jawaban", "Gambar Jawaban Tidak Ditemukan!");
    }

    private String generateFilename(MultipartFile file){
        String originalFilename = file.getOriginalFilename();
        String extension = (originalFilename != null && originalFilename.contains("."))
                ? originalFilename.substring(originalFilename.lastIndexOf("."))
                : "";
        return UUID.randomUUID() + extension;
    }

    private void ensureBucketExists() throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        if (!minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build())) {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
        }
    }

    private void uploadFile(MultipartFile file, String path) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        ensureBucketExists();
        minioClient.putObject(
                PutObjectArgs.builder()
                        .bucket(bucketName)
                        .object(path)
                        .stream(file.getInputStream(), file.getSize(), -1)
                        .contentType(file.getContentType())
                        .build()
        );
    }

    private ResponseWithMessageAndData<Map<String, String>> buildResponse(HttpStatus httpStatus, String message, Map<String, String> data) {
        ResponseWithMessageAndData<Map<String, String>> responseWithMessageAndData = new ResponseWithMessageAndData<>();
        responseWithMessageAndData.setHttpCode(httpStatus.value());
        responseWithMessageAndData.setMessage(message);
        responseWithMessageAndData.setData(data);
        return responseWithMessageAndData;
    }

    private ResponseWithMessageAndData<Map<String, String>> generateUrlResponse(String objectPath, String successMessage, String notFoundMessage) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        try {
            minioClient.statObject(StatObjectArgs.builder().bucket(bucketName).object(objectPath).build());

            String url = minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder()
                            .bucket(bucketName)
                            .object(objectPath)
                            .method(Method.GET)
                            .expiry(60)
                    .build());

            return buildResponse(HttpStatus.OK, successMessage, Map.of("url", url));
        } catch (MinioException exception) {
            String errorMessage = exception.getMessage().contains("Object does not exist") ? notFoundMessage : exception.getMessage();
            HttpStatus httpStatus = exception.getMessage().contains("Object does not exist") ? HttpStatus.NOT_FOUND : HttpStatus.INTERNAL_SERVER_ERROR;
            return buildResponse(httpStatus, errorMessage, null);
        }
    }
}
