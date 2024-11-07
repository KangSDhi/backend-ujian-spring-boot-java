package dev.kangsdhi.backendujianspringbootjava.validators.implementation;

import dev.kangsdhi.backendujianspringbootjava.dto.request.SiswaRequest;
import dev.kangsdhi.backendujianspringbootjava.repository.PenggunaRepository;
import dev.kangsdhi.backendujianspringbootjava.validators.SiswaUniqueIDSiswa;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SiswaUniqueIDSiswaValidator implements ConstraintValidator<SiswaUniqueIDSiswa, List<SiswaRequest>> {

    @Autowired
    private PenggunaRepository penggunaRepository;

    @Override
    public boolean isValid(List<SiswaRequest> siswaRequests, ConstraintValidatorContext constraintValidatorContext) {

        if (siswaRequests == null || siswaRequests.isEmpty()) {
            return true;
        }

        Set<String> uniqueIdSiswa = new HashSet<>();
        boolean hasDuplicates = false;

        for (int i = 0; i < siswaRequests.size(); i++) {
            String idSiswa = siswaRequests.get(i).getIdSiswa();

            if (!uniqueIdSiswa.add(idSiswa) && idSiswa != null) {
                hasDuplicates = true;
                constraintValidatorContext.disableDefaultConstraintViolation();
                constraintValidatorContext.buildConstraintViolationWithTemplate("Duplikasi ID Siswa "+idSiswa+"!")
                        .addPropertyNode("data["+i+"].idSiswa")
                        .addConstraintViolation();
            }

            if (penggunaRepository.existsByIdSiswa(idSiswa) && idSiswa != null) {
                hasDuplicates = true;
                constraintValidatorContext.disableDefaultConstraintViolation();
                constraintValidatorContext.buildConstraintViolationWithTemplate("ID Siswa "+idSiswa+" Sudah Terdaftar!")
                        .addPropertyNode("data["+i+"].idSiswa")
                        .addConstraintViolation();
            }
        }

        return !hasDuplicates;
    }
}
