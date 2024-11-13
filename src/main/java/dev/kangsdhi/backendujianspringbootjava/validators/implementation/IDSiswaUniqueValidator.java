package dev.kangsdhi.backendujianspringbootjava.validators.implementation;

import dev.kangsdhi.backendujianspringbootjava.repository.PenggunaRepository;
import dev.kangsdhi.backendujianspringbootjava.validators.IDSiswaUnique;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class IDSiswaUniqueValidator implements ConstraintValidator<IDSiswaUnique, String> {

    @Autowired
    private PenggunaRepository penggunaRepository;

    @Override
    public boolean isValid(String IdSiswa, ConstraintValidatorContext constraintValidatorContext) {
        if (IdSiswa == null || IdSiswa.isEmpty()){
            return true;
        }

        return !penggunaRepository.existsByIdSiswa(IdSiswa);
    }
}
