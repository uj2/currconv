package org.balaguta.currconv.web;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Constraint(validatedBy = UserDtoValidator.class)
@Target(TYPE)
@Retention(RUNTIME)
@Documented
public @interface ValidUserDto {

    String message() default "{org.balaguta.currconv.User.message}";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
