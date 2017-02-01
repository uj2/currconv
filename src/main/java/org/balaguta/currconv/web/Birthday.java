package org.balaguta.currconv.web;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Constraint(validatedBy = BirthdayValidator.class)
@Target({FIELD, METHOD})
@Retention(RUNTIME)
@Documented
public @interface Birthday {

    String message() default "{org.balaguta.currconv.User.Birthday.message}";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
