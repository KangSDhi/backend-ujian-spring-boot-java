package dev.kangsdhi.backendujianspringbootjava.validators.implementation;

import dev.kangsdhi.backendujianspringbootjava.repository.TingkatRepository;
import dev.kangsdhi.backendujianspringbootjava.validators.NamaTingkatUnique;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class NamaTingkatUniqueValidator implements ConstraintValidator<NamaTingkatUnique, String> {

    @Autowired
    private TingkatRepository tingkatRepository;

    @Override
    public boolean isValid(String namaTingkat, ConstraintValidatorContext constraintValidatorContext) {
        return !tingkatRepository.existsByTingkat(namaTingkat);
    }
}
