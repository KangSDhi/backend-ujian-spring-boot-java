package dev.kangsdhi.backendujianspringbootjava.validators.implementation;

import dev.kangsdhi.backendujianspringbootjava.dto.request.TingkatEditRequest;
import dev.kangsdhi.backendujianspringbootjava.repository.TingkatRepository;
import dev.kangsdhi.backendujianspringbootjava.validators.NamaTingkatUniqueExistById;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

public class NamaTingkatUniqueExistByIdValidator implements ConstraintValidator<NamaTingkatUniqueExistById, TingkatEditRequest> {

    @Autowired
    private TingkatRepository tingkatRepository;

    @Override
    public boolean isValid(TingkatEditRequest tingkatEditRequest, ConstraintValidatorContext constraintValidatorContext) {

        if (tingkatEditRequest.getIdTingkat() == null || tingkatEditRequest.getNamaTingkat() == null) {
            return true;
        }

        UUID tingkatId = UUID.fromString(tingkatEditRequest.getIdTingkat());

        if (tingkatRepository.existsByIdAndTingkat(tingkatId, tingkatEditRequest.getNamaTingkat())) {
            return true;
        }

        return !tingkatRepository.existsByTingkat(tingkatEditRequest.getNamaTingkat());
    }
}
