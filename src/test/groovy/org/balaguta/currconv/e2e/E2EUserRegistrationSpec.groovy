package org.balaguta.currconv.e2e

import org.balaguta.currconv.e2e.page.RegisterPage

import java.util.concurrent.atomic.AtomicInteger

class E2EUserRegistrationSpec extends E2EAbstractSpec {
    def NOT_EMPTY_ERROR = "may not be empty"

    def counter = new AtomicInteger()

    def "as unauthenticated user I can see proper registration form"() {

        when: "registration page is opened"
        def page = RegisterPage.open(driver, baseUrl)

        then: "registration form contains all necessary controls"
        exists page.email
        exists page.firstName
        exists page.lastName
        exists page.password
        exists page.repeatPassword
        exists page.birthday
        exists page.addressLine1
        exists page.addressLine2
        exists page.addressZip
        exists page.addressCity
        exists page.addressCountry
        exists page.submitButton
    }

    def "as unauthenticated user I can register an account"() {

        when: "registration page is opened"
        def page = RegisterPage.open(driver, baseUrl)

        and: "valid details entered into the form"
        page = page.registerWithSuccess(validForm())

        then: "user is automatically logged in and redirected to index page"
        page.body.text.contains "Welcome, Jack Flap!"
    }

    def "if I don't enter any data, I see corresponding error messages"() {

        when: "registration page is opened"
        def page = RegisterPage.open(driver, baseUrl)

        and: "no details entered into the form"
        page = page.registerWithErrors()

        then: "appropriate error messages are displayed"
        page.hasError "email", NOT_EMPTY_ERROR
        page.hasError "firstName", NOT_EMPTY_ERROR
        page.hasError "lastName", NOT_EMPTY_ERROR
        page.hasError "repeatPassword", NOT_EMPTY_ERROR
        page.hasError "birthday", "may not be null"
        page.hasError "address.line1", NOT_EMPTY_ERROR
        page.hasNoError "address.line2"
        page.hasError "address.zip", NOT_EMPTY_ERROR
        page.hasError "address.city", NOT_EMPTY_ERROR
    }

    def "if I don't enter correct email, I see error message"() {

        when: "registration page is opened"
        def page = RegisterPage.open(driver, baseUrl)

        and: "incorrect email entered into the form"
        def form = validForm()
        form['email'] = 'invalid_email'
        page = page.registerWithErrors(form)

        then: "appropriate error message is displayed"
        page.hasError "email", "not a well-formed email address"
    }

    private Map validForm(Map custom) {
        def id = counter.incrementAndGet()
        def result = [
                email: "jack${id}@example.com",
                firstName: 'Jack',
                lastName: 'Flap',
                password: 'jacknonsense',
                repeatPassword: 'jacknonsense',
                birthday: '30/Dec/1950',
                addressLine1: '99 Heartbreak Ave',
                addressZip: '99-123',
                addressCity: 'Aarhus',
                addressCountry: 'Denmark'
        ]
        if (custom != null) {
            result.putAll(custom)
        }
        return result
    }

}
