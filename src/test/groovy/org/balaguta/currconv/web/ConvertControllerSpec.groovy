package org.balaguta.currconv.web

import org.balaguta.currconv.app.AppUser
import org.balaguta.currconv.data.UserRepository
import org.balaguta.currconv.data.entity.Conversion
import org.balaguta.currconv.data.entity.MoneyAmount
import org.balaguta.currconv.data.entity.User
import org.balaguta.currconv.service.ConversionService
import org.balaguta.currconv.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.format.support.DefaultFormattingConversionService
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.MvcResult
import spock.lang.Specification
import spock.mock.DetachedMockFactory

import java.time.LocalDate
import java.time.Month

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@WebMvcTest(ConvertController)
class ConvertControllerSpec extends Specification {

    @Autowired
    private ConversionService conversionService

    @Autowired
    private MockMvc mockMvc

    def "POST / converts currencies for authenticated user"() {
        given:
        conversionService.getSupportedCurrencies() >> ['EUR', 'PLN', 'UAH', 'USD']
        _ * conversionService.getHistory() >> []
        1 * conversionService.convert(new MoneyAmount(new BigDecimal("123.45"), "EUR"), "UAH",
                LocalDate.of(2017, Month.FEBRUARY, 6)) >> new Conversion()

        def userEntity = new User()
        userEntity.with {
            id = 1
            email = "joe@example.com"
        }
        def userDetails = new AppUser("joe@example.com", "...", userEntity, [
                new SimpleGrantedAuthority("ROLE_1"),
                new SimpleGrantedAuthority("ROLE_2"),
                new SimpleGrantedAuthority("PERMISSION_1"),
                new SimpleGrantedAuthority("PERMISSION_2"),
                new SimpleGrantedAuthority("PERMISSION_3")
        ])

        expect:
        MvcResult result = mockMvc.perform(post("/")
                    .sessionAttr("fromCurrency", "USD")
                    .sessionAttr("toCurrency", "PLN")
                    .sessionAttr("ratesFrom", LocalDate.of(2017, Month.FEBRUARY, 5))
                    .param("fromCurrency", "EUR")
                    .param("toCurrency", "UAH")
                    .param("amount", "123.45")
                    .param("ratesFrom", "06/Feb/2017")
                    .with(user(userDetails))
                    .with(csrf()))
                .andExpect(request().asyncStarted())
                .andReturn()

        mockMvc.perform(asyncDispatch(result))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/?results"))
                .andExpect(request().sessionAttribute("fromCurrency", "EUR"))
                .andExpect(request().sessionAttribute("toCurrency", "UAH"))
                .andExpect(request().sessionAttribute("ratesFrom", LocalDate.of(2017, Month.FEBRUARY, 6)))
    }


    @TestConfiguration
    static class MockConfig {
        def detachedMockFactory = new DetachedMockFactory();

        @Bean
        UserService userService() {
            detachedMockFactory.Stub(UserService)
        }

        @Bean
        ConversionService conversionServiceMy() {
            detachedMockFactory.Mock(ConversionService)
        }

        @Bean
        DefaultFormattingConversionService conversionService() {
            new DefaultFormattingConversionService()
        }

        @Bean
        UserRepository userRepository() {
            detachedMockFactory.Stub(UserRepository)
        }
    }

}
