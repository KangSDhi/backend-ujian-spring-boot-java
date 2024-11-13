package dev.kangsdhi.backendujianspringbootjava.validators.implementation;

import dev.kangsdhi.backendujianspringbootjava.dto.request.SiswaItemBatchRequest;
import dev.kangsdhi.backendujianspringbootjava.validators.SiswaBatchRequestMin;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;

public class SiswaBatchRequestMinValidator implements ConstraintValidator<SiswaBatchRequestMin, List<SiswaItemBatchRequest>> {

    @Override
    public boolean isValid(List<SiswaItemBatchRequest> siswaItemBatchRequestList, ConstraintValidatorContext constraintValidatorContext) {
        return siswaItemBatchRequestList != null && !siswaItemBatchRequestList.isEmpty();
    }
}
