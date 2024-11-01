package dev.kangsdhi.backendujianspringbootjava.validators.implementation;

import dev.kangsdhi.backendujianspringbootjava.repository.SoalRepository;
import dev.kangsdhi.backendujianspringbootjava.validators.NamaSoalUnique;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class NamaSoalUniqueValidator implements ConstraintValidator<NamaSoalUnique, String> {

    @Autowired
    private SoalRepository soalRepository;

    @Override
    public boolean isValid(String namaSoal, ConstraintValidatorContext constraintValidatorContext) {
        return !soalRepository.existsByNamaSoal(namaSoal);
    }
}
