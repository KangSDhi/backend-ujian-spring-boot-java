package dev.kangsdhi.backendujianspringbootjava.validators;

import dev.kangsdhi.backendujianspringbootjava.validators.implementation.SiswaBatchRequestCheckKelasValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = SiswaBatchRequestCheckKelasValidator.class)
public @interface SiswaBatchRequestCheckKelas {
    String message() default "Siswa Check Kelas";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
