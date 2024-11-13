package dev.kangsdhi.backendujianspringbootjava.validators.implementation;

import dev.kangsdhi.backendujianspringbootjava.dto.request.SiswaItemBatchRequest;
import dev.kangsdhi.backendujianspringbootjava.repository.PenggunaRepository;
import dev.kangsdhi.backendujianspringbootjava.validators.SiswaBatchRequestUniqueNamaSiswa;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SiswaBatchRequestUniqueNamaSiswaValidator implements ConstraintValidator<SiswaBatchRequestUniqueNamaSiswa, List<SiswaItemBatchRequest>> {

    @Autowired
    private PenggunaRepository penggunaRepository;

    @Override
    public boolean isValid(List<SiswaItemBatchRequest> siswaItemBatchRequestList, ConstraintValidatorContext constraintValidatorContext) {

        if (siswaItemBatchRequestList == null || siswaItemBatchRequestList.isEmpty()) {
            return true;
        }

        Set<String> uniqueSiswaNames = new HashSet<>();
        boolean hasDuplicates = false;

        constraintValidatorContext.disableDefaultConstraintViolation();

        for (int i = 0; i < siswaItemBatchRequestList.size(); i++) {
            String namaSiswa = siswaItemBatchRequestList.get(i).getNama_siswa();

            if (namaSiswa != null) {

                if (!uniqueSiswaNames.add(namaSiswa)) {
                    hasDuplicates = true;
                    constraintValidatorContext.buildConstraintViolationWithTemplate("Duplikasi Nama Siswa "+namaSiswa+"!")
                        .addPropertyNode("data["+i+"].nama_siswa")
                        .addConstraintViolation();
                }

                if (penggunaRepository.existsByNamaPengguna(namaSiswa)) {
                    hasDuplicates = true;
                    constraintValidatorContext.buildConstraintViolationWithTemplate("Nama Siswa "+namaSiswa+" Sudah Terdaftar!")
                        .addPropertyNode("data["+i+"].nama_siswa")
                        .addConstraintViolation();
                }
            }
        }

        return !hasDuplicates;
    }
}
