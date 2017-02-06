package org.balaguta.currconv.web

import org.apache.commons.lang3.builder.EqualsBuilder
import org.balaguta.currconv.data.UserRepository
import org.balaguta.currconv.data.entity.Country
import org.balaguta.currconv.data.entity.Permission
import org.balaguta.currconv.data.entity.Role
import org.balaguta.currconv.data.entity.User
import org.balaguta.currconv.service.UserService
import org.hamcrest.BaseMatcher
import org.hamcrest.Description
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.http.MediaType
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification
import spock.mock.DetachedMockFactory

import java.time.LocalDate

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@WebMvcTest(RegisterController)
class RegisterControllerSpec extends Specification {

    @Autowired
    private UserService userService

    @Autowired
    private UserRepository userRepository

    @Autowired
    private MockMvc mockMvc

    def countries = [
            new Country(id: 1, name: 'Germany'),
            new Country(id: 2, name: 'Poland'),
            new Country(id: 3, name: 'Ukraine')
    ]

    def "GET /register populates model correctly"() {
        given:
        userService.getCountries() >> countries
        def userDto = new UserDto()
        userDto.with {
            address = new UserDto.Address()
        }

        expect:
        mockMvc.perform(get("/register"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("register"))
                .andExpect(model().attribute("countries", countries))
                .andExpect(model().attribute("userDto", userDtoMatcher(userDto)))
    }

    def "POST /register registers and auto-logins the user"() {
        given:
        def userDto = new UserDto()
        userDto.with {
            email = "joe@example.com"
            firstName = "Joe"
            lastName = "Simple"
            password = "nobrainer"
            repeatPassword = "nobrainer"
            birthday = LocalDate.parse("1927-05-20")
            address = new UserDto.Address()
            address.with {
                line1 = "99 Nowhere St."
                city = "Pleasantville"
                zip = "99-999"
                country = 1
            }
        }
        def perm1 = new Permission(id: 1, name: "PERMISSION_1")
        def perm2 = new Permission(id: 2, name: "PERMISSION_2")
        def perm3 = new Permission(id: 3, name: "PERMISSION_3")
        def role1 = new Role(id: 1, name: "ROLE_1", permissions: [perm1, perm2])
        def role2 = new Role(id: 2, name: "ROLE_2", permissions: [perm2, perm3])
        def userEntity = new User()
        userEntity.with {
            id = 1
            email = "joe@example.com"
            password = "\$2a\$10\$M5dq9S5agiZBcUrFoa0szeNH6wselrTtiMowl9QPmUk5VX9qr.f42"
            roles << role1 << role2
        }

        userService.getCountries() >> countries
        userService.userExists("joe@example.com") >> false
        userService.registerUser(userDto)

        // user details request
        userRepository.findByEmail("joe@example.com") >> userEntity

        expect:
        mockMvc.perform(post("/register")
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .param("email", "joe@example.com")
                    .param("firstName", "Joe")
                    .param("lastName", "Simple")
                    .param("password", "nobrainer")
                    .param("repeatPassword", "nobrainer")
                    .param("birthday", "20/May/1927")
                    .param("address.line1", "99 Nowhere St.")
                    .param("address.city", "Pleasantville")
                    .param("address.zip", "99-999")
                    .param("address.country", "1"))
                .andExpect(status().isFound())
                .andExpect(authenticated().withUsername("joe@example.com").withAuthorities([
                        new SimpleGrantedAuthority("ROLE_1"),
                        new SimpleGrantedAuthority("ROLE_2"),
                        new SimpleGrantedAuthority("PERMISSION_1"),
                        new SimpleGrantedAuthority("PERMISSION_2"),
                        new SimpleGrantedAuthority("PERMISSION_3")
                ]))
                .andExpect(redirectedUrl("/"))
    }

    Object userDtoMatcher(UserDto that) {
        return new BaseMatcher<UserDto>() {
            @Override
            boolean matches(Object item) {
                return EqualsBuilder.reflectionEquals(that, item, "address") &&
                    EqualsBuilder.reflectionEquals(((UserDto) item).address, that.address)
            }

            @Override
            void describeTo(Description description) {

            }
        }
    }

    @TestConfiguration
    static class MockConfig {
        def detachedMockFactory = new DetachedMockFactory();

        @Bean
        UserService userService() {
            return detachedMockFactory.Stub(UserService)
        }

        @Bean
        UserRepository userRepository() {
            return detachedMockFactory.Stub(UserRepository)
        }
    }
}
