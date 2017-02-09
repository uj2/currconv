package org.balaguta.currconv.e2e.page

import groovy.transform.InheritConstructors
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindBy
import org.openqa.selenium.support.ui.Select

import java.time.LocalDate
import java.time.format.DateTimeFormatter

@InheritConstructors
class AuthenticatedIndexPage extends AbstractCurrencyConverterPage {

    @FindBy(tagName = "BODY") WebElement body
    WebElement amount
    WebElement fromCurrency
    WebElement toCurrency
    WebElement ratesFrom
    @FindBy(xpath = "//form[1]") WebElement conversionForm
    @FindBy(xpath = "//form[2]") WebElement logoutForm
    @FindBy(className = "jumbotron") WebElement jumbotron

    def convert(BigDecimal amount, String fromCurrency, String toCurrency, LocalDate ratesFrom) {
        this.amount.sendKeys amount.toString()
        new Select(this.fromCurrency).selectByVisibleText fromCurrency
        new Select(this.toCurrency).selectByVisibleText toCurrency
        this.ratesFrom.clear()
        this.ratesFrom.sendKeys ratesFrom.format(DateTimeFormatter.ofPattern('dd/MMM/yyyy'))
        conversionForm.submit()
        init AuthenticatedIndexPage
    }
}
