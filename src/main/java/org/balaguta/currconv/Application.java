package org.balaguta.currconv;

import org.balaguta.currconv.app.AppUserDetailsService;
import org.balaguta.currconv.app.CurrconvProperties;
import org.balaguta.currconv.data.UserRepository;
import org.balaguta.currconv.web.RegisterController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.convert.ConversionService;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
@EnableConfigurationProperties(CurrconvProperties.class)
@EnableCaching
public class Application extends WebMvcConfigurerAdapter {

    @Bean
    public RestOperations restOperations() {
        return new RestTemplate();
    }

    @Bean
    public ConversionService conversionService() {
        return new DefaultFormattingConversionService();
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/register").setViewName(RegisterController.VIEW_NAME);
    }

    @Configuration
    public static class SecurityConfig {

        @Autowired
        private UserRepository userRepository;

        @Bean
        public PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }

        @Bean
        public UserDetailsService userDetailsService() {
            return new AppUserDetailsService(userRepository);
        }

        @Autowired
        public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(userDetailsService())
                    .passwordEncoder(passwordEncoder());
        }
    }

    @Configuration
    @Order(1)
    public class H2ConsoleAccessConfig extends WebSecurityConfigurerAdapter {
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.antMatcher("/h2-console/**")
                    .csrf().disable()
                    .headers().disable()
                    .authorizeRequests()
                        .anyRequest()
                        .hasAuthority("admin:h2-console");
        }

    }

    @Configuration
    public static class AppAccessConfig extends WebSecurityConfigurerAdapter {

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .authorizeRequests()
                        .antMatchers("/", "/register").permitAll()
                        .anyRequest().authenticated()
                    .and()
                    .formLogin()
                        .loginPage("/login")
                            .permitAll()
                    .and()
                    .logout()
                        .permitAll();
        }

    }

    public static void main(String[] args) throws Throwable {
        SpringApplication.run(Application.class, args);
    }
}
