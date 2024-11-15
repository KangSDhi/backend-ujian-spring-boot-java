package dev.kangsdhi.backendujianspringbootjava.validators.implementation;

import dev.kangsdhi.backendujianspringbootjava.repository.KelasRepository;
import dev.kangsdhi.backendujianspringbootjava.validators.CheckKelasExist;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class CheckKelasExistValidator implements ConstraintValidator<CheckKelasExist, String> {

    @Autowired
    private KelasRepository kelasRepository;

    @Override
    public boolean isValid(String namaKelas, ConstraintValidatorContext constraintValidatorContext) {

        if (namaKelas == null) {
            return true;
        }

        return kelasRepository.existsByKelas(namaKelas);
    }
}
