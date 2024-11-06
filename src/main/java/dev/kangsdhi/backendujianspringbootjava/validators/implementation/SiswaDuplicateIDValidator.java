package dev.kangsdhi.backendujianspringbootjava.validators.implementation;

import dev.kangsdhi.backendujianspringbootjava.dto.request.SiswaRequest;
import dev.kangsdhi.backendujianspringbootjava.validators.SiswaDuplicateID;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SiswaDuplicateIDValidator implements ConstraintValidator<SiswaDuplicateID, List<SiswaRequest>> {

    @Override
    public boolean isValid(List<SiswaRequest> siswaRequests, ConstraintValidatorContext constraintValidatorContext) {

        if (siswaRequests == null || siswaRequests.isEmpty()) {
            return true;
        }

        Set<String> uniqueSiswaIds = new HashSet<>();
        boolean hasDuplicates = false;

        for (int i = 0; i < siswaRequests.size(); i++) {
            String idSiswa = siswaRequests.get(i).getIdSiswa();
            if (!uniqueSiswaIds.add(idSiswa) && idSiswa != null) {
                hasDuplicates = true;
                constraintValidatorContext.disableDefaultConstraintViolation();
                constraintValidatorContext.buildConstraintViolationWithTemplate("Duplicate ID Siswa at index [" + i + "]")
                        .addPropertyNode("data["+i+"].idSiswa")
                        .addConstraintViolation();
            }
        }

        return !hasDuplicates;
    }
}
