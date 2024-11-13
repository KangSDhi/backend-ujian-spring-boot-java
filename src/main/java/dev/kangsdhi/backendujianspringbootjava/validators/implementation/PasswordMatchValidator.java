package dev.kangsdhi.backendujianspringbootjava.validators.implementation;

import dev.kangsdhi.backendujianspringbootjava.validators.PasswordMatch;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.BeanUtils;

import java.util.Objects;

public class PasswordMatchValidator implements ConstraintValidator<PasswordMatch, Object> {
    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext constraintValidatorContext) {
        try {
            String password = (String) Objects.requireNonNull(BeanUtils.getPropertyDescriptor(obj.getClass(), "password"))
                    .getReadMethod().invoke(obj);
            String konfirmasiPassword = (String) Objects.requireNonNull(BeanUtils.getPropertyDescriptor(obj.getClass(), "konfirmasi_password"))
                    .getReadMethod().invoke(obj);
            return password != null && password.equals(konfirmasiPassword);
        } catch (Exception e) {
            return false;
        }
    }
}
