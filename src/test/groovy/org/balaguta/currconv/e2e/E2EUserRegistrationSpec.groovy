package org.balaguta.currconv.e2e

import org.balaguta.currconv.e2e.page.RegisterPage

class E2EUserRegistrationSpec extends E2EAbstractSpec {

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
        page = page.register(
                email: "jack@example.com",
                firstName: 'Jack',
                lastName: 'Flap',
                password: 'jacknonsense',
                repeatPassword: 'jacknonsense',
                birthday: '30/Dec/1950',
                addressLine1: '99 Heartbreak Ave',
                addressZip: '99-123',
                addressCity: 'Aarhus',
                addressCountry: 'Denmark')

        then:
        page.body.text.contains "Welcome, Jack Flap!"
    }

}
