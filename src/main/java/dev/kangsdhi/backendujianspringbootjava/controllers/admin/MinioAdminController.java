package dev.kangsdhi.backendujianspringbootjava.controllers.admin;

import dev.kangsdhi.backendujianspringbootjava.dto.request.FileUploadRequest;
import dev.kangsdhi.backendujianspringbootjava.dto.response.ResponseWithMessageAndData;
import dev.kangsdhi.backendujianspringbootjava.services.MinioService;
import io.minio.errors.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/minio")
@RequiredArgsConstructor
public class MinioAdminController {

    @Autowired
    private MinioService minioService;

    @PostMapping("/upload/gambar-pertanyaan")
    public ResponseEntity<ResponseWithMessageAndData<Map<String, String>>> uploadGambarPertanyaan(@Valid @ModelAttribute FileUploadRequest fileUploadRequest, BindingResult bindingResult) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {

        if (bindingResult.hasErrors()) {
            throw new BadRequestException(bindingResult.getAllErrors().getFirst().getDefaultMessage());
        }

        ResponseWithMessageAndData<Map<String, String>> response = minioService.uploadGambarPertanyaan(fileUploadRequest.getFile());
        HttpStatus httpStatus = HttpStatus.valueOf(response.getHttp_code());
        return new ResponseEntity<>(response, httpStatus);
    }

    @GetMapping("/get/gambar-pertanyaan")
    public ResponseEntity<ResponseWithMessageAndData<Map<String, String>>> getGambarPertanyaan(@RequestParam String gambarPertanyaan) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        ResponseWithMessageAndData<Map<String, String>> response = minioService.getUrlGambarPertanyaan(gambarPertanyaan);
        HttpStatus httpStatus = HttpStatus.valueOf(response.getHttp_code());
        return new ResponseEntity<>(response, httpStatus);
    }

    @PostMapping("/upload/gambar-jawaban")
    public ResponseEntity<ResponseWithMessageAndData<Map<String, String>>> uploadGambarJawaban(@Valid @ModelAttribute FileUploadRequest fileUploadRequest, BindingResult bindingResult) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {

        if (bindingResult.hasErrors()){
            throw new BadRequestException(bindingResult.getAllErrors().getFirst().getDefaultMessage());
        }

        ResponseWithMessageAndData<Map<String, String>> response = minioService.uploadGambarJawaban(fileUploadRequest.getFile());
        HttpStatus httpStatus = HttpStatus.valueOf(response.getHttp_code());
        return new ResponseEntity<>(response, httpStatus);
    }

    @GetMapping("/get/gambar-jawaban")
    public ResponseEntity<ResponseWithMessageAndData<Map<String, String>>> getGambarJawaban(@RequestParam String gambarJawaban) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        ResponseWithMessageAndData<Map<String, String>> response = minioService.getUrlGambarJawaban(gambarJawaban);
        HttpStatus httpStatus = HttpStatus.valueOf(response.getHttp_code());
        return new ResponseEntity<>(response, httpStatus);
    }
}
