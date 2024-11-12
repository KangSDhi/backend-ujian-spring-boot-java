package dev.kangsdhi.backendujianspringbootjava.validators.implementation;

import dev.kangsdhi.backendujianspringbootjava.dto.request.SiswaRequest;
import dev.kangsdhi.backendujianspringbootjava.repository.KelasRepository;
import dev.kangsdhi.backendujianspringbootjava.validators.SiswaRequestCheckKelas;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class SiswaRequestCheckKelasValidator implements ConstraintValidator<SiswaRequestCheckKelas, List<SiswaRequest>> {

    @Autowired
    private KelasRepository kelasRepository;

    @Override
    public boolean isValid(List<SiswaRequest> siswaRequestList, ConstraintValidatorContext constraintValidatorContext) {

        if (siswaRequestList == null || siswaRequestList.isEmpty()) {
            return true;
        }

        boolean hasKelasExist = true;
        constraintValidatorContext.disableDefaultConstraintViolation();

        for (int i = 0; i < siswaRequestList.size(); i++) {
            String namaKelas = siswaRequestList.get(i).getKelasSiswa();
            if (namaKelas != null) {
                if (!kelasRepository.existsByKelas(namaKelas)) {
                    constraintValidatorContext.buildConstraintViolationWithTemplate("Kelas "+namaKelas+" Tidak Ditemukan!")
                            .addPropertyNode("data["+i+"].namaKelas")
                            .addConstraintViolation();
                    hasKelasExist = false;
                }
            }
        }

        return hasKelasExist;
    }
}
