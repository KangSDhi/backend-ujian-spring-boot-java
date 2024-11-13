package dev.kangsdhi.backendujianspringbootjava.validators;

import dev.kangsdhi.backendujianspringbootjava.validators.implementation.SiswaBatchRequestMinValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = SiswaBatchRequestMinValidator.class)
public @interface SiswaBatchRequestMin {
    String message() default "Siswa Request Min";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
