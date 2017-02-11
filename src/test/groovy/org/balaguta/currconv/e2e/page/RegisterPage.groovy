package org.balaguta.currconv.e2e.page

import groovy.transform.InheritConstructors
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindBy
import org.openqa.selenium.support.ui.Select

@InheritConstructors
class RegisterPage extends AbstractCurrencyConverterPage {

    @FindBy(id = "registration-form")
    WebElement registrationForm
    WebElement email
    WebElement firstName
    WebElement lastName
    WebElement password
    WebElement repeatPassword
    WebElement birthday
    @FindBy(id = "address.line1")
    WebElement addressLine1
    @FindBy(id = "address.line2")
    WebElement addressLine2
    @FindBy(id = "address.zip")
    WebElement addressZip
    @FindBy(id = "address.city")
    WebElement addressCity
    @FindBy(id = "address.country")
    WebElement addressCountry
    @FindBy(xpath = "//form[@id='registration-form']//input[@type='submit']")
    WebElement submitButton

    def register(Map args) {
        args.each {
            if (it.key == 'addressCountry') {
                new Select(addressCountry).selectByVisibleText it.value.toString()
            } else {
                this."${it.key}".clear()
                this."${it.key}".sendKeys it.value
            }
        }
        registrationForm.submit()
        init AuthenticatedIndexPage
    }

    static open(WebDriver driver, URL baseUrl) {
        goTo(new URL(baseUrl, "/register"), driver, RegisterPage)
    }

}
