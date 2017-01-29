package org.balaguta.currconv.web;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UserDtoValidator implements ConstraintValidator<ValidUserDto, UserDto> {
    @Override
    public void initialize(ValidUserDto constraintAnnotation) {

    }

    @Override
    public boolean isValid(UserDto object, ConstraintValidatorContext context) {
        if (object == null) {
            return true;
        }
        if (!object.getPassword().equals(object.getRepeatPassword())) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("{org.balaguta.currconv.User.repeatPassword.message}")
                    .addPropertyNode("repeatPassword")
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}
