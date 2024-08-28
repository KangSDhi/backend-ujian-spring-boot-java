package dev.kangsdhi.backendujianspringbootjava.dto.request;

import dev.kangsdhi.backendujianspringbootjava.validators.FileSize;
import dev.kangsdhi.backendujianspringbootjava.validators.FileFormat;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class FileUploadRequest {

    @NotNull(message = "File must not be null")
    @FileSize(max = 1024 * 1024, message = "File Maksimal 1MB")
    @FileFormat(allowed = {"image/jpeg", "image/png"}, message = "File Format Harus .jpg, .jpeg atau .png")
    private MultipartFile file;

}
