package org.balaguta.currconv.e2e.page

import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindBy

class LoginPage extends AbstractCurrencyConverterPage {

    @FindBy(tagName = "FORM") WebElement form
    WebElement username
    WebElement password

    LoginPage(WebDriver driver) {
        super(driver)
        assert driver.title == 'Currency Converter'
    }

    def login(String email, String password) {
        username.sendKeys email
        this.password.sendKeys password
        form.submit()
        init AuthenticatedIndexPage
    }

    static open(WebDriver driver, URL baseUrl) {
        goTo(new URL(baseUrl, "/login"), driver, LoginPage)
    }
}
