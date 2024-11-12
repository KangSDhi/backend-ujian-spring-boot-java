package dev.kangsdhi.backendujianspringbootjava.validators.implementation;

import dev.kangsdhi.backendujianspringbootjava.dto.request.SiswaRequest;
import dev.kangsdhi.backendujianspringbootjava.validators.SiswaRequestMin;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;

public class SiswaRequestMinValidator implements ConstraintValidator<SiswaRequestMin, List<SiswaRequest>> {

    @Override
    public boolean isValid(List<SiswaRequest> siswaRequestList, ConstraintValidatorContext constraintValidatorContext) {
        return siswaRequestList != null && !siswaRequestList.isEmpty();
    }
}
