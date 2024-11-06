package dev.kangsdhi.backendujianspringbootjava.validators.implementation;

import dev.kangsdhi.backendujianspringbootjava.dto.request.SiswaRequest;
import dev.kangsdhi.backendujianspringbootjava.repository.PenggunaRepository;
import dev.kangsdhi.backendujianspringbootjava.validators.SiswaUniqueIDSiswa;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class SiswaUniqueIDSiswaValidator implements ConstraintValidator<SiswaUniqueIDSiswa, List<SiswaRequest>> {

    @Autowired
    private PenggunaRepository penggunaRepository;

    @Override
    public boolean isValid(List<SiswaRequest> siswaRequests, ConstraintValidatorContext constraintValidatorContext) {

        if (siswaRequests == null || siswaRequests.isEmpty()) {
            return true;
        }

        boolean hasNonUnique = false;

        for (int i = 0; i < siswaRequests.size(); i++) {
            String idSiswa = siswaRequests.get(i).getIdSiswa();
            if (penggunaRepository.existsByIdSiswa(idSiswa) && idSiswa != null) {
                hasNonUnique = true;
                constraintValidatorContext.disableDefaultConstraintViolation();
                constraintValidatorContext.buildConstraintViolationWithTemplate("Duplicate Unique ID Siswa at index [" + i + "]")
                        .addPropertyNode("data["+i+"].idSiswa")
                        .addConstraintViolation();
            }
        }

        return !hasNonUnique;
    }
}
