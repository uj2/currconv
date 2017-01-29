package org.balaguta.currconv.web

import spock.lang.Specification

import javax.validation.ConstraintValidatorContext

class UserDtoValidatorSpec extends Specification {
    def validator = new UserDtoValidator()
    def context = Mock(ConstraintValidatorContext)

    def violationBuilder = Mock(ConstraintValidatorContext.ConstraintViolationBuilder)
    def nodeBuilder = Mock(ConstraintValidatorContext.ConstraintViolationBuilder.NodeBuilderCustomizableContext)
    def failedContext = Mock(ConstraintValidatorContext) {
        buildConstraintViolationWithTemplate(_) >> violationBuilder
        violationBuilder.addPropertyNode(_) >> nodeBuilder
        nodeBuilder.addConstraintViolation() >> failedContext
    }

    def "validates null object"() {

        expect:
        validator.isValid(null, context)
    }

    def "validates object with null password"() {
        def dto = new UserDto()

        expect:
        validator.isValid(dto, context)
    }

    def "validates when passwords match"() {
        def dto = new UserDto()
        dto.password = 'abc'
        dto.repeatPassword = 'abc'

        expect:
        validator.isValid(dto, context)
    }

    def "does not validate object when passwords don't match"() {
        def dto1 = new UserDto()
        dto1.password = 'abc'

        def dto2 = new UserDto()
        dto2.repeatPassword = 'abc'

        def dto3 = new UserDto()
        dto3.password = 'abc'
        dto3.repeatPassword = 'def'

        expect:
        !validator.isValid(dto1, failedContext)
        !validator.isValid(dto2, failedContext)
        !validator.isValid(dto3, failedContext)
    }

    def "sets error message when passwords don't match"() {
        def dto = new UserDto()
        dto.password = 'abc'
        dto.repeatPassword = 'def'

        when:
        validator.isValid(dto, failedContext)

        then:
        1 * failedContext.buildConstraintViolationWithTemplate("{org.balaguta.currconv.User.repeatPassword.message}") >>
                violationBuilder
        1 * violationBuilder.addPropertyNode("repeatPassword") >> nodeBuilder

    }
}
