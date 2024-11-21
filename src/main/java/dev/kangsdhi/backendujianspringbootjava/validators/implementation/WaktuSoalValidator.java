package dev.kangsdhi.backendujianspringbootjava.validators.implementation;

import dev.kangsdhi.backendujianspringbootjava.dto.request.SoalRequest;
import dev.kangsdhi.backendujianspringbootjava.utils.ConvertUtils;
import dev.kangsdhi.backendujianspringbootjava.validators.WaktuSoalValid;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Date;

public class WaktuSoalValidator implements ConstraintValidator<WaktuSoalValid, SoalRequest> {

    private static final ConvertUtils convertUtils = new ConvertUtils();

    @Override
    public boolean isValid(SoalRequest soalRequest, ConstraintValidatorContext constraintValidatorContext) {
        if (soalRequest.getWaktu_mulai_soal() == null || soalRequest.getWaktu_selesai_soal() == null) {
            return true;
        }

        if (soalRequest.getWaktu_mulai_soal().contains("NaN") || soalRequest.getWaktu_selesai_soal().contains("NaN")) {
            return true;
        }

        Date waktuMulai = convertUtils.convertStringToDatetimeOrTime(soalRequest.getWaktu_mulai_soal());
        Date waktuSelesai = convertUtils.convertStringToDatetimeOrTime(soalRequest.getWaktu_selesai_soal());
        return waktuMulai.before(waktuSelesai);
    }
}
