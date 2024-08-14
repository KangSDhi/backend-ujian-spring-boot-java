package dev.kangsdhi.backendujianspringbootjava.services;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface MinioDevService {
    String testConnection();
    String uploadFile(MultipartFile file) throws Exception;
    String getFileUrl(String fileName) throws Exception;
    Resource downloadFile(String fileName) throws Exception;
}
