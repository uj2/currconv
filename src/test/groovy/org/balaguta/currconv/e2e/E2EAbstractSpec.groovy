package org.balaguta.currconv.e2e

import com.saucelabs.common.SauceOnDemandAuthentication
import org.balaguta.currconv.e2e.support.SauceOnDemand
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.htmlunit.HtmlUnitDriver
import org.openqa.selenium.remote.DesiredCapabilities
import org.openqa.selenium.remote.RemoteWebDriver
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.context.embedded.LocalServerPort
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

import java.util.concurrent.TimeUnit

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@SauceOnDemand
abstract class E2EAbstractSpec extends Specification {

    WebDriver driver
    @LocalServerPort int port
    URL baseUrl

    @Value('${sauce.username}')
    String sauceUsername
    @Value('${sauce.access-key}')
    String sauceAccessKey
    @Value('${CI:#{false}}')
    boolean continuousIntegration
    SauceOnDemandAuthentication sauceAuthentication = new SauceOnDemandAuthentication()

    def setup() {
        sauceAuthentication.setUsername(sauceUsername)
        sauceAuthentication.setAccessKey(sauceAccessKey)

        baseUrl = new URL("http://127.0.0.1:${port}")
        if (!continuousIntegration) {
            driver = new HtmlUnitDriver()
        } else {
            DesiredCapabilities caps = DesiredCapabilities.chrome();
            def hubUrl = new URL("http://$sauceUsername:$sauceAccessKey@localhost:4445/wd/hub")
            driver = new RemoteWebDriver(hubUrl, caps)
        }
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
