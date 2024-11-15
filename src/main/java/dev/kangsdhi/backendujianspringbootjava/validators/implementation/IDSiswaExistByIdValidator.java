package dev.kangsdhi.backendujianspringbootjava.validators.implementation;

import dev.kangsdhi.backendujianspringbootjava.dto.request.SiswaEditRequest;
import dev.kangsdhi.backendujianspringbootjava.repository.PenggunaRepository;
import dev.kangsdhi.backendujianspringbootjava.validators.IDSiswaExistById;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

public class IDSiswaExistByIdValidator implements ConstraintValidator<IDSiswaExistById, SiswaEditRequest> {

    @Autowired
    private PenggunaRepository penggunaRepository;

    @Override
    public boolean isValid(SiswaEditRequest siswaEditRequest, ConstraintValidatorContext constraintValidatorContext) {

        if (siswaEditRequest.getId() == null || siswaEditRequest.getId_siswa() == null) {
            return true;
        }

        UUID siswaId = UUID.fromString(siswaEditRequest.getId());

        if (penggunaRepository.existsByIdAndIdSiswa(siswaId, siswaEditRequest.getId_siswa())){
            return true;
        }

        return !penggunaRepository.existsByIdSiswa(siswaEditRequest.getId_siswa());
    }
}
