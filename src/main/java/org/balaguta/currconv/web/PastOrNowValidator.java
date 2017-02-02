package org.balaguta.currconv.web;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.temporal.Temporal;

public class PastOrNowValidator implements ConstraintValidator<PastOrNow, Temporal> {
    @Override
    public void initialize(PastOrNow constraintAnnotation) {
        // nothing to do
    }

    @Override
    public boolean isValid(Temporal value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        if (value instanceof LocalDate) {
            LocalDate d = (LocalDate) value;
            return d.isBefore(LocalDate.now()) || d.isEqual(LocalDate.now());
        } else if (value instanceof LocalDateTime) {
            LocalDateTime d = (LocalDateTime) value;
            return d.isBefore(LocalDateTime.now()) || d.isEqual(LocalDateTime.now());
        } else if (value instanceof ZonedDateTime) {
            ZonedDateTime d = (ZonedDateTime) value;
            return d.isBefore(ZonedDateTime.now()) || d.isEqual(ZonedDateTime.now());
        } else {
            throw new IllegalArgumentException("unsupported field type - " + value.getClass());
        }
    }
}
