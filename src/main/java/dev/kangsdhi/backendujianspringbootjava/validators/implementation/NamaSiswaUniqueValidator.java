package dev.kangsdhi.backendujianspringbootjava.validators.implementation;

import dev.kangsdhi.backendujianspringbootjava.repository.PenggunaRepository;
import dev.kangsdhi.backendujianspringbootjava.validators.NamaSiswaUnique;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class NamaSiswaUniqueValidator implements ConstraintValidator<NamaSiswaUnique, String> {

    @Autowired
    private PenggunaRepository penggunaRepository;

    @Override
    public boolean isValid(String namaSiswa, ConstraintValidatorContext constraintValidatorContext) {
        if (namaSiswa == null || namaSiswa.isEmpty()) {
            return true;
        }
        return !penggunaRepository.existsByNamaPengguna(namaSiswa);
    }
}
