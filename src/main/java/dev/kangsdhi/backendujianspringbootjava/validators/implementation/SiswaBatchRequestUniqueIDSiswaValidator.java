package dev.kangsdhi.backendujianspringbootjava.validators.implementation;

import dev.kangsdhi.backendujianspringbootjava.dto.request.SiswaItemBatchRequest;
import dev.kangsdhi.backendujianspringbootjava.repository.PenggunaRepository;
import dev.kangsdhi.backendujianspringbootjava.validators.SiswaBatchRequestUniqueIDSiswa;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SiswaBatchRequestUniqueIDSiswaValidator implements ConstraintValidator<SiswaBatchRequestUniqueIDSiswa, List<SiswaItemBatchRequest>> {

    @Autowired
    private PenggunaRepository penggunaRepository;

    @Override
    public boolean isValid(List<SiswaItemBatchRequest> siswaItemBatchRequests, ConstraintValidatorContext constraintValidatorContext) {

        if (siswaItemBatchRequests == null || siswaItemBatchRequests.isEmpty()) {
            return true;
        }

        Set<String> uniqueIdSiswa = new HashSet<>();
        boolean hasDuplicates = false;

        constraintValidatorContext.disableDefaultConstraintViolation();

        for (int i = 0; i < siswaItemBatchRequests.size(); i++) {
            String idSiswa = siswaItemBatchRequests.get(i).getId_siswa();

            if (idSiswa != null){
                if (!uniqueIdSiswa.add(idSiswa)) {
                    hasDuplicates = true;
                    constraintValidatorContext.buildConstraintViolationWithTemplate("Duplikasi ID Siswa "+idSiswa+"!")
                            .addPropertyNode("data["+i+"].id_siswa")
                            .addConstraintViolation();
                }

                if (penggunaRepository.existsByIdSiswa(idSiswa)) {
                    hasDuplicates = true;
                    constraintValidatorContext.buildConstraintViolationWithTemplate("ID Siswa "+idSiswa+" Sudah Terdaftar!")
                            .addPropertyNode("data["+i+"].id_siswa")
                            .addConstraintViolation();
                }
            }
        }

        return !hasDuplicates;
    }
}
