package org.balaguta.currconv.e2e

import org.balaguta.currconv.e2e.page.LoginPage

import java.time.LocalDate
import java.time.Month

class E2EConversionSpec extends E2EAbstractSpec {

    def "as a user I can convert currency and see the result"() {
        when: "login page is opened"
        def page = LoginPage.open(driver, baseUrl)

        and: "valid credentials are entered"
        page = page.login("joe@example.com", "joe123")

        and: "conversion is invoked"
        page = page.convert(123.4, "PLN", "EUR", LocalDate.of(2016, Month.FEBRUARY, 9))


        then: "conversion results are displayed"
        assert page.jumbotron.text == "123.40 PLN 27.71 EUR"
    }

}
