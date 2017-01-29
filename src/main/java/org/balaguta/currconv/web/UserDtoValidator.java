package org.balaguta.currconv.web;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

public class UserDtoValidator implements ConstraintValidator<ValidUserDto, UserDto> {
    @Override
    public void initialize(ValidUserDto constraintAnnotation) {
        // nothing to do
    }

    @Override
    public boolean isValid(UserDto object, ConstraintValidatorContext context) {
        if (object == null) {
            return true;
        }
        if (!Objects.equals(object.getPassword(), object.getRepeatPassword())) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("{org.balaguta.currconv.User.repeatPassword.message}")
                    .addPropertyNode("repeatPassword")
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}
