package dev.kangsdhi.backendujianspringbootjava.validators.implementation;

import dev.kangsdhi.backendujianspringbootjava.validators.FileFormat;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

public class FileFormatValidator implements ConstraintValidator<FileFormat, MultipartFile> {

    private String[] allowedFormat;

    @Override
    public void initialize(FileFormat constraintAnnotation) {
        this.allowedFormat = constraintAnnotation.allowed();
    }

    @Override
    public boolean isValid(MultipartFile multipartFile, ConstraintValidatorContext constraintValidatorContext) {
        if (multipartFile == null) {
            return true;
        }

        String contentType = multipartFile.getContentType();
        for (String format : allowedFormat) {
            if (contentType.equals(format)) {
                return true;
            }
        }
        return false;
    }
}
