package dev.kangsdhi.backendujianspringbootjava.validators;

import dev.kangsdhi.backendujianspringbootjava.validators.implementation.SiswaDuplicateIDValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = SiswaDuplicateIDValidator.class)
public @interface SiswaDuplicateID {
    String message() default "Duplicate ID";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
