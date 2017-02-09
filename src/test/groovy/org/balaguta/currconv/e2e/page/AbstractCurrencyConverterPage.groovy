package org.balaguta.currconv.e2e.page

import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.PageFactory

abstract class AbstractCurrencyConverterPage {

    WebDriver driver

    AbstractCurrencyConverterPage(WebDriver driver) {
        this.driver = driver
    }

    def <T extends AbstractCurrencyConverterPage> T init(Class<T> pageClass) {
        PageFactory.initElements driver, pageClass
    }

    static <T extends AbstractCurrencyConverterPage> T goTo(URL url, WebDriver driver, Class<T> pageClass) {
        driver.get url.toString()
        PageFactory.initElements driver, pageClass
    }
}
