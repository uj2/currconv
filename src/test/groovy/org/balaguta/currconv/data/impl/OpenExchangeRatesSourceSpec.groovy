package org.balaguta.currconv.data.impl

import org.balaguta.currconv.app.CurrconvProperties
import org.balaguta.currconv.data.entity.OpenExchangeRatesLatest
import org.springframework.web.client.RestOperations
import spock.lang.Specification

class OpenExchangeRatesSourceSpec extends Specification {

    def rates = new OpenExchangeRatesLatest()
    def restOperations = Mock(RestOperations) {
        _ * getForObject(new URI("https://openexchangerates.org:1234/api/latest.json?app_id=1234"),
                OpenExchangeRatesLatest) >> rates
    }
    def properties = new CurrconvProperties()

    def ratesSource = new OpenExchangeRatesSource(restOperations, properties)

    def setup() {
        properties.with {
            openExchangeRates = new CurrconvProperties.OpenExchangeRates()
            openExchangeRates.with {
                url = new URI("https://openexchangerates.org:1234/api/latest.json")
                appId = "1234"
            }
        }
        rates.rates = [ 'UAH': 27.176155, 'PLN': 4.039738 ]
    }

    def "returns rates by reaching to OpenExchangeRates"() {

        when:
        def actual = ratesSource.getLatestRates()

        then:
        actual == [ 'UAH': 27.176155, 'PLN': 4.039738 ]
        1 * restOperations.getForObject(new URI("https://openexchangerates.org:1234/api/latest.json?app_id=1234"),
                OpenExchangeRatesLatest) >> rates
    }
}
