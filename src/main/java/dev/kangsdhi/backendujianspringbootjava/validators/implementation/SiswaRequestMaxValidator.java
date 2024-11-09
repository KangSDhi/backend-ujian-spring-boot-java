package dev.kangsdhi.backendujianspringbootjava.validators.implementation;

import dev.kangsdhi.backendujianspringbootjava.dto.request.SiswaRequest;
import dev.kangsdhi.backendujianspringbootjava.validators.SiswaRequestMax;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;

public class SiswaRequestMaxValidator implements ConstraintValidator<SiswaRequestMax, List<SiswaRequest>> {

    @Override
    public boolean isValid(List<SiswaRequest> siswaRequestList, ConstraintValidatorContext constraintValidatorContext) {
        if (siswaRequestList == null || siswaRequestList.isEmpty()) {
            return true;
        }

        boolean hasMoreThanTheMaxValue = false;
        int maxValue = 50;

        constraintValidatorContext.disableDefaultConstraintViolation();

        if (siswaRequestList.size() > maxValue) {
            hasMoreThanTheMaxValue = true;
            constraintValidatorContext.buildConstraintViolationWithTemplate("Jumlah Item Lebih Dari "+maxValue+" Item")
                    .addConstraintViolation();

        }

        return !hasMoreThanTheMaxValue;
    }
}
