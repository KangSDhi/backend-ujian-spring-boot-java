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
        if (soalRequest.getWaktuMulaiSoal() == null || soalRequest.getWaktuSelesaiSoal() == null) {
            return true;
        }

        if (soalRequest.getWaktuMulaiSoal().contains("Nan") || soalRequest.getWaktuSelesaiSoal().contains("Nan")) {
            return true;
        }

        Date waktuMulai = convertUtils.convertStringToDatetimeOrTime(soalRequest.getWaktuMulaiSoal());
        Date waktuSelesai = convertUtils.convertStringToDatetimeOrTime(soalRequest.getWaktuSelesaiSoal());
        return waktuMulai.before(waktuSelesai);
    }
}
