package dev.kangsdhi.backendujianspringbootjava.validators.implementation;

import dev.kangsdhi.backendujianspringbootjava.dto.request.JurusanEditRequest;
import dev.kangsdhi.backendujianspringbootjava.repository.JurusanRepository;
import dev.kangsdhi.backendujianspringbootjava.validators.NamaJurusanUniqueExistById;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

public class NamaJurusanUniqueExistByIdValidator implements ConstraintValidator<NamaJurusanUniqueExistById, JurusanEditRequest> {

    @Autowired
    private JurusanRepository jurusanRepository;

    @Override
    public boolean isValid(JurusanEditRequest jurusanEditRequest, ConstraintValidatorContext constraintValidatorContext) {

        if (jurusanEditRequest.getIdJurusan() == null || jurusanEditRequest.getNamaJurusan() == null) {
            return true;
        }

        UUID jurusanId = UUID.fromString(jurusanEditRequest.getIdJurusan());

        if (jurusanRepository.existsByIdAndJurusan(jurusanId, jurusanEditRequest.getNamaJurusan())) {
            return true;
        }

        return !jurusanRepository.existsByJurusan(jurusanEditRequest.getNamaJurusan());
    }
}
