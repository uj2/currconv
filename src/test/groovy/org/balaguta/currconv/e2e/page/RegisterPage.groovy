package org.balaguta.currconv.e2e.page

import groovy.transform.InheritConstructors
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindBy
import org.openqa.selenium.support.ui.Select

import java.util.concurrent.TimeUnit

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

    @FindBy(className = "has-error")
    List<WebElement> hasErrors


    def registerWithSuccess(Map args) {
        filloutRegistrationForm(args)
        registrationForm.submit()
        init AuthenticatedIndexPage
    }

    private void filloutRegistrationForm(Map parameters) {
        parameters.each {
            if (it.key == 'addressCountry') {
                new Select(addressCountry).selectByVisibleText it.value.toString()
            } else {
                this."${it.key}".clear()
                this."${it.key}".sendKeys it.value
            }
        }
    }

    def registerWithErrors(Map args) {
        filloutRegistrationForm(args)
        registrationForm.submit()
        init RegisterPage
    }

    def hasError(String fieldName, String errorText) {
        notimeout {
            hasErrors.any {
                !it.findElements(By.id(fieldName)).isEmpty() &&
                        !it.findElements(By.className("help-block")).isEmpty() &&
                        it.findElement(By.className("help-block")).text == errorText
            }
        }
    }

    private <T> T notimeout(Closure<T> closure) {
        driver.manage().timeouts().implicitlyWait 0, TimeUnit.SECONDS
        T result = closure.call()
        driver.manage().timeouts().implicitlyWait 10, TimeUnit.SECONDS
        return result
    }

    def hasNoError(String fieldName) {
        notimeout {
            !hasErrors.any { !it.findElements(By.id(fieldName)).isEmpty() }
        }
    }


    static open(WebDriver driver, URL baseUrl) {
        goTo(new URL(baseUrl, "/register"), driver, RegisterPage)
    }
}
