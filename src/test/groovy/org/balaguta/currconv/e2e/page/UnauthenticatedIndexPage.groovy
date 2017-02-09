package org.balaguta.currconv.e2e.page

import groovy.transform.InheritConstructors
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindBy

@InheritConstructors
class UnauthenticatedIndexPage extends AbstractCurrencyConverterPage {

    @FindBy(tagName = "BODY") WebElement body

    static open(WebDriver driver, URL baseUrl) {
        goTo(baseUrl, driver, UnauthenticatedIndexPage)
    }
}
