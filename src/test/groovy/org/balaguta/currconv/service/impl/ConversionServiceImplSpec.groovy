package org.balaguta.currconv.service.impl

import org.balaguta.currconv.app.CurrconvProperties
import static org.balaguta.currconv.app.CurrconvProperties.OpenExchangeRates
import org.balaguta.currconv.data.ConversionRepository
import org.balaguta.currconv.data.Converter
import org.balaguta.currconv.data.entity.Conversion
import org.balaguta.currconv.data.entity.MoneyAmount
import org.balaguta.currconv.data.entity.User
import org.balaguta.currconv.service.UserService
import spock.lang.Specification

import java.time.LocalDateTime

class ConversionServiceImplSpec extends Specification {

    def converter = Mock(Converter)
    def conversionRepository = Mock(ConversionRepository)
    def userService = Mock(UserService)
    def properties = new CurrconvProperties()
    def service

    def setup() {
        properties.with {
            supportedCurrencies = ['EUR', 'USD', 'GBP', 'NZD', 'AUD', 'PLN']
            openExchangeRates = new OpenExchangeRates()
            openExchangeRates.with {
                url = new URI("https://openexchangerates.org/api/latest.json")
                appId = "12345"
            }
        }
        service = new ConversionServiceImpl(converter, conversionRepository, userService, properties);
    }

    def "returns sorted list of supported currencies"() {

        expect:
        service.getSupportedCurrencies() == [ 'AUD', 'EUR', 'GBP', 'NZD', 'PLN', 'USD' ]
    }

    def "converts and persists money value"() {

        def sourceAmount = new MoneyAmount(123.45, "PLN")
        def targetAmount = new MoneyAmount(28.50, "EUR")

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
        println now

        when:
        def actual = service.convert(sourceAmount, "EUR")

        then:
        userService.getCurrentUser() >> currentUser

        converter.convert(sourceAmount, "EUR") >> targetAmount

        1 * conversionRepository.save({
            it.source == sourceAmount && it.target == targetAmount && it.timestamp.isAfter(now) && it.user.is(currentUser)
        }) >> conversion

        actual.is(conversion)
    }
}
