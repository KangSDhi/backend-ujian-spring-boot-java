package dev.kangsdhi.backendujianspringbootjava.validators.implementation;

import dev.kangsdhi.backendujianspringbootjava.dto.request.SiswaEditRequest;
import dev.kangsdhi.backendujianspringbootjava.repository.PenggunaRepository;
import dev.kangsdhi.backendujianspringbootjava.validators.NamaSiswaExistById;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

public class NamaSiswaExistByIdValidator implements ConstraintValidator<NamaSiswaExistById, SiswaEditRequest> {

    @Autowired
    private PenggunaRepository penggunaRepository;

    @Override
    public boolean isValid(SiswaEditRequest siswaEditRequest, ConstraintValidatorContext constraintValidatorContext) {

        if (siswaEditRequest.getId() == null || siswaEditRequest.getNama_siswa() == null) {
            return true;
        }

        UUID siswaId = UUID.fromString(siswaEditRequest.getId());

        if (penggunaRepository.existsByIdAndNamaPengguna(siswaId, siswaEditRequest.getNama_siswa())){
            return true;
        }

        return !penggunaRepository.existsByNamaPengguna(siswaEditRequest.getNama_siswa());
    }
}
