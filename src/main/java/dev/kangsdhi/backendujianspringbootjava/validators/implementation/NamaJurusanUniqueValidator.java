package dev.kangsdhi.backendujianspringbootjava.validators.implementation;

import dev.kangsdhi.backendujianspringbootjava.repository.JurusanRepository;
import dev.kangsdhi.backendujianspringbootjava.validators.NamaJurusanUnique;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class NamaJurusanUniqueValidator implements ConstraintValidator<NamaJurusanUnique, String> {

    @Autowired
    private JurusanRepository jurusanRepository;

    @Override
    public boolean isValid(String namaJurusan, ConstraintValidatorContext constraintValidatorContext) {
        return !jurusanRepository.existsByJurusan(namaJurusan);
    }
}
