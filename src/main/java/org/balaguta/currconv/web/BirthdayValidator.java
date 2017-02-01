package org.balaguta.currconv.web;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

import static java.time.Month.JANUARY;

public class BirthdayValidator implements ConstraintValidator<Birthday, LocalDate> {
    @Override
    public void initialize(Birthday constraintAnnotation) {
        // nothing to do
    }

    @Override
    public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
        return value == null || (value.isAfter(LocalDate.of(1900, JANUARY, 1))
                && value.isBefore(LocalDate.now()));
    }
}
