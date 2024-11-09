package dev.kangsdhi.backendujianspringbootjava.validators;

import dev.kangsdhi.backendujianspringbootjava.validators.implementation.SiswaRequestMaxValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = SiswaRequestMaxValidator.class)
public @interface SiswaRequestMax {
    String message() default "Siswa Request Lebih";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
