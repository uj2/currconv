package org.balaguta.currconv.service.impl

import org.balaguta.currconv.app.CurrconvProperties
import org.balaguta.currconv.data.ConversionRepository
import org.balaguta.currconv.data.ExchangeRatesSource
import org.balaguta.currconv.data.entity.Conversion
import org.balaguta.currconv.data.entity.MoneyAmount
import org.balaguta.currconv.data.entity.User
import org.balaguta.currconv.service.UserService
import spock.lang.Specification

import java.time.LocalDateTime

import static org.balaguta.currconv.app.CurrconvProperties.OpenExchangeRates

class ConversionServiceImplSpec extends Specification {

    def ratesSource = Mock(ExchangeRatesSource)
    def conversionRepository = Mock(ConversionRepository)
    def userService = Mock(UserService)
    def properties = new CurrconvProperties()
    def service

    def setup() {
        properties.with {
            supportedCurrencies = ['EUR', 'USD', 'GBP', 'NZD', 'AUD', 'PLN', 'UAH']
            openExchangeRates = new OpenExchangeRates()
            openExchangeRates.with {
                latestUrl = new URI("https://openexchangerates.org/api/latest.json")
                historicalUrl = "https://openexchangerates.org/api/historical/{ratesFrom}.json"
                appId = "12345"
            }
        }
        service = new ConversionServiceImpl(ratesSource, conversionRepository, userService, properties);
    }

    def "returns sorted list of supported currencies"() {

        expect:
        service.getSupportedCurrencies() == [ 'AUD', 'EUR', 'GBP', 'NZD', 'PLN', 'UAH', 'USD' ]
    }

    def "converts and persists money value"() {

        def sourceAmount = new MoneyAmount(123.45, "UAH")
        def targetAmount = new MoneyAmount(18.35085413, "PLN")

        def currentUser = new User()
        currentUser.id = 1
        currentUser.email = "joe@example.com"

        def conversion = new Conversion()
        conversion.with {
            source = sourceAmount
            target = targetAmount
            user = currentUser
        }

        def now = LocalDateTime.now()

        when:
        def actual = service.convert(sourceAmount, "PLN")

        then:
        userService.getCurrentUser() >> currentUser
        1 * ratesSource.getLatestRates() >> [ 'UAH': 27.176155, 'PLN': 4.039738 ]

        1 * conversionRepository.save({
            it.source == sourceAmount && it.target == targetAmount && it.timestamp.isAfter(now) && it.user.is(currentUser)
        }) >> conversion

        actual.is(conversion)
    }


    def "handles unsupported source currency"() {

        when:
        service.convert(new MoneyAmount(123.45, "NZD"), "PLN")

        then:
        1 * ratesSource.getLatestRates() >> [ 'UAH': 27.176155, 'PLN': 4.039738 ]
        thrown(IllegalArgumentException)
    }


    def "handles unsupported target currency"() {

        when:
        service.convert(new MoneyAmount(123.45, "NZD"), "PLN")

        then:
        1 * ratesSource.getLatestRates() >> [ 'UAH': 27.176155, 'PLN': 4.039738 ]
        thrown(IllegalArgumentException)
    }

}
