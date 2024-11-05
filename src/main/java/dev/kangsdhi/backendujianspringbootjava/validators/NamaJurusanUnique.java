package dev.kangsdhi.backendujianspringbootjava.validators;

import dev.kangsdhi.backendujianspringbootjava.validators.implementation.NamaJurusanUniqueValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NamaJurusanUniqueValidator.class)
public @interface NamaJurusanUnique {
    String message() default "Nama Jurusan Terdaftar";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
