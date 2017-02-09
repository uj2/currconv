package org.balaguta.currconv.e2e

import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.htmlunit.HtmlUnitDriver
import org.springframework.boot.context.embedded.LocalServerPort
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

import java.util.concurrent.TimeUnit

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
abstract class E2EAbstractSpec extends Specification {

    WebDriver driver
    @LocalServerPort int port
    URL baseUrl

    def setup() {
        baseUrl = new URL("http://127.0.0.1:${port}")
        driver = new HtmlUnitDriver()
        driver.manage().timeouts().implicitlyWait 10, TimeUnit.SECONDS
    }

    def cleanup() {
        driver.quit()
    }


    def exists(WebElement element) {
        try {
            element.displayed
        } catch (NoSuchElementException e) {
            throw new AssertionError("$element does not exist")
        }
    }
}
