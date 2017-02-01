package org.balaguta.currconv.app;

import lombok.Data;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.net.URI;
import java.time.Duration;
import java.util.List;

@ConfigurationProperties(prefix = "currconv")
@Data
public class CurrconvProperties {
    @NotEmpty
    @Size(min = 2)
    private List<String> supportedCurrencies;
    @NotNull
    private OpenExchangeRates openExchangeRates;
    @NotNull
    private UserProperties admin;
    private Duration conversionCacheTtl;

    @Data
    public static class OpenExchangeRates {
        @NotNull
        private URI url;
        @NotNull
        private String appId;
    }

    @Data
    public static class UserProperties {
        @Email
        @NotNull
        private String email;
        @NotNull
        private String password;
    }
}
