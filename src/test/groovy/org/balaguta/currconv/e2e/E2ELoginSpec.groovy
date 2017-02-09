package org.balaguta.currconv.e2e

import org.balaguta.currconv.e2e.page.LoginPage
import org.balaguta.currconv.e2e.page.UnauthenticatedIndexPage

class E2ELoginSpec extends E2EAbstractSpec {

    def "as an unauthenticated user I see a welcome message on main screen"() {
        when: "index page is opened"
        def page = UnauthenticatedIndexPage.open(driver, baseUrl)

        then: "it contains a welcome message"
        page.body.text.contains 'Welcome! Please log in to start converting currencies.'
    }

    def "as a user I can log in and see main screen"() {
        when: "login page is opened"
        def page = LoginPage.open(driver, baseUrl)

        and: "valid credentials are entered"
        page = page.login("joe@example.com", "joe123")

        then: "proper currency conversion screen shows up"
        page.body.text.contains "Welcome, Joe Simple!"
        exists page.amount
        exists page.fromCurrency
        exists page.toCurrency
        exists page.ratesFrom
        exists page.logoutForm
    }
}
