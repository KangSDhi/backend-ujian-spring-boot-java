package dev.kangsdhi.backendujianspringbootjava.validators;

import dev.kangsdhi.backendujianspringbootjava.validators.implementation.NamaSoalUniqueExistByIdValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NamaSoalUniqueExistByIdValidator.class)
public @interface NamaSoalUniqueExistById {
    String message() default "Nama soal sudah terdaftar";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
