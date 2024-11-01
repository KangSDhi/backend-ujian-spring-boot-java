package dev.kangsdhi.backendujianspringbootjava.validators.implementation;

import dev.kangsdhi.backendujianspringbootjava.dto.request.SoalEditRequest;
import dev.kangsdhi.backendujianspringbootjava.repository.SoalRepository;
import dev.kangsdhi.backendujianspringbootjava.validators.NamaSoalUniqueExistById;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

public class NamaSoalUniqueExistByIdValidator implements ConstraintValidator<NamaSoalUniqueExistById, SoalEditRequest> {

    @Autowired
    private SoalRepository soalRepository;

    @Override
    public boolean isValid(SoalEditRequest soalEditRequest, ConstraintValidatorContext constraintValidatorContext) {

        if (soalEditRequest.getIdSoal() == null || soalEditRequest.getNamaSoal() == null) {
            return true;
        }

        UUID soalId = UUID.fromString(soalEditRequest.getIdSoal());

        if (soalRepository.existsByIdAndNamaSoal(soalId, soalEditRequest.getNamaSoal())) {
            return true;
        }

        return !soalRepository.existsByNamaSoal(soalEditRequest.getNamaSoal());
    }
}
