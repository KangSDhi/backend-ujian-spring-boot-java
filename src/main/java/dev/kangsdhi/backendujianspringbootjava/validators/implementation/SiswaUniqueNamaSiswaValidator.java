package dev.kangsdhi.backendujianspringbootjava.validators.implementation;

import dev.kangsdhi.backendujianspringbootjava.dto.request.SiswaRequest;
import dev.kangsdhi.backendujianspringbootjava.repository.PenggunaRepository;
import dev.kangsdhi.backendujianspringbootjava.validators.SiswaUniqueNamaSiswa;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SiswaUniqueNamaSiswaValidator implements ConstraintValidator<SiswaUniqueNamaSiswa, List<SiswaRequest>> {

    @Autowired
    private PenggunaRepository penggunaRepository;

    @Override
    public boolean isValid(List<SiswaRequest> siswaRequestList, ConstraintValidatorContext constraintValidatorContext) {

        if (siswaRequestList == null || siswaRequestList.isEmpty()) {
            return true;
        }

        Set<String> uniqueSiswaNames = new HashSet<>();
        boolean hasDuplicates = false;

        constraintValidatorContext.disableDefaultConstraintViolation();

        for (int i = 0; i < siswaRequestList.size(); i++) {
            String namaSiswa = siswaRequestList.get(i).getNamaSiswa();

            if (namaSiswa != null) {

                if (!uniqueSiswaNames.add(namaSiswa)) {
                    hasDuplicates = true;
                    constraintValidatorContext.buildConstraintViolationWithTemplate("Duplikasi Nama Siswa "+namaSiswa+"!")
                        .addPropertyNode("data["+i+"].namaSiswa")
                        .addConstraintViolation();
                }

                if (penggunaRepository.existsByNamaPengguna(namaSiswa)) {
                    hasDuplicates = true;
                    constraintValidatorContext.buildConstraintViolationWithTemplate("Nama Siswa "+namaSiswa+" Sudah Terdaftar!")
                        .addPropertyNode("data["+i+"].namaSiswa")
                        .addConstraintViolation();
                }
            }
        }

        return !hasDuplicates;
    }
}
