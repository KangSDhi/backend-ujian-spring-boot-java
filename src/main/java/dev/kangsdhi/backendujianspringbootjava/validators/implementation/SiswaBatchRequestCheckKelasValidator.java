package dev.kangsdhi.backendujianspringbootjava.validators.implementation;

import dev.kangsdhi.backendujianspringbootjava.dto.request.SiswaItemBatchRequest;
import dev.kangsdhi.backendujianspringbootjava.repository.KelasRepository;
import dev.kangsdhi.backendujianspringbootjava.validators.SiswaBatchRequestCheckKelas;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class SiswaBatchRequestCheckKelasValidator implements ConstraintValidator<SiswaBatchRequestCheckKelas, List<SiswaItemBatchRequest>> {

    @Autowired
    private KelasRepository kelasRepository;

    @Override
    public boolean isValid(List<SiswaItemBatchRequest> siswaItemBatchRequestList, ConstraintValidatorContext constraintValidatorContext) {

        if (siswaItemBatchRequestList == null || siswaItemBatchRequestList.isEmpty()) {
            return true;
        }

        boolean hasKelasExist = true;
        constraintValidatorContext.disableDefaultConstraintViolation();

        for (int i = 0; i < siswaItemBatchRequestList.size(); i++) {
            String namaKelas = siswaItemBatchRequestList.get(i).getKelas();
            if (namaKelas != null) {
                if (!kelasRepository.existsByKelas(namaKelas)) {
                    constraintValidatorContext.buildConstraintViolationWithTemplate("Kelas "+namaKelas+" Tidak Ditemukan!")
                            .addPropertyNode("data["+i+"].kelas")
                            .addConstraintViolation();
                    hasKelasExist = false;
                }
            }
        }

        return hasKelasExist;
    }
}
