package org.balaguta.currconv.data.impl

import org.balaguta.currconv.app.CurrconvProperties
import org.balaguta.currconv.data.entity.MoneyAmount
import org.balaguta.currconv.data.entity.OpenExchangeRatesLatest
import org.springframework.web.client.RestOperations
import spock.lang.Specification

class OpenExchangeConverterSpec extends Specification {

    def rates = new OpenExchangeRatesLatest()
    def restOperations = Mock(RestOperations) {
        _ * getForObject(new URI("https://openexchangerates.org:1234/api/latest.json?app_id=1234"),
                OpenExchangeRatesLatest) >> rates
    }
    def properties = new CurrconvProperties()

    def converter = new OpenExchangeRatesConverter(restOperations, properties)

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

    def "converts money value using OpenExchangeRates"() {

        when:
        def plnValue = converter.convert(new MoneyAmount(123.45, "UAH"), "PLN")

        then:
        plnValue.currency == 'PLN'
        plnValue.amount == BigDecimal.valueOf(18.35085413)
        1 * restOperations.getForObject(new URI("https://openexchangerates.org:1234/api/latest.json?app_id=1234"),
                OpenExchangeRatesLatest) >> rates
    }

    def "handles unsupported source currency"() {

        when:
        converter.convert(new MoneyAmount(123.45, "AUD"), "PLN")

        then:
        thrown(IllegalArgumentException)
    }


    def "handles unsupported target currency"() {

        when:
        converter.convert(new MoneyAmount(123.45, "UAH"), "AUD")

        then:
        thrown(IllegalArgumentException)
    }

}
