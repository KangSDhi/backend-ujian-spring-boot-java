package dev.kangsdhi.backendujianspringbootjava.validators.implementation;

import dev.kangsdhi.backendujianspringbootjava.dto.request.SiswaItemBatchRequest;
import dev.kangsdhi.backendujianspringbootjava.validators.SiswaBatchRequestMax;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;

public class SiswaBatchRequestMaxValidator implements ConstraintValidator<SiswaBatchRequestMax, List<SiswaItemBatchRequest>> {

    @Override
    public boolean isValid(List<SiswaItemBatchRequest> siswaItemBatchRequestList, ConstraintValidatorContext constraintValidatorContext) {
        if (siswaItemBatchRequestList == null || siswaItemBatchRequestList.isEmpty()) {
            return true;
        }

        boolean hasMoreThanTheMaxValue = false;
        int maxValue = 100;

        constraintValidatorContext.disableDefaultConstraintViolation();

        if (siswaItemBatchRequestList.size() > maxValue) {
            hasMoreThanTheMaxValue = true;
            constraintValidatorContext.buildConstraintViolationWithTemplate("Jumlah Item Lebih Dari "+maxValue+" Item")
                    .addConstraintViolation();

        }

        return !hasMoreThanTheMaxValue;
    }
}
