package org.balaguta.currconv.data.impl;

import org.balaguta.currconv.app.CurrconvProperties;
import org.balaguta.currconv.data.Converter;
import org.balaguta.currconv.data.entity.MoneyAmount;
import org.balaguta.currconv.data.entity.OpenExchangeRatesLatest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;
import org.springframework.web.client.RestOperations;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URI;

@Repository
public class OpenExchangeRatesConverter implements Converter {

    private static final String APP_ID_PARAM = "app_id";

    private final RestOperations restOperations;
    private final CurrconvProperties properties;

    @Autowired
    public OpenExchangeRatesConverter(RestOperations restOperations, CurrconvProperties properties) {
        this.restOperations = restOperations;
        this.properties = properties;

        Assert.notNull(this.restOperations, "[restOperations] required; must not be null");
        Assert.notNull(this.properties, "[properties] required; must not be null");
    }

    @Override
    public MoneyAmount convert(MoneyAmount amount, String targetCurrency) {
        URI uri = UriComponentsBuilder.fromUri(properties.getOpenExchangeRates().getUrl())
                .queryParam(APP_ID_PARAM, properties.getOpenExchangeRates().getAppId())
                .build()
                .toUri();
        OpenExchangeRatesLatest rates = restOperations.getForObject(uri, OpenExchangeRatesLatest.class);
        Assert.isTrue(rates.getRates().containsKey(amount.getCurrency()), "[amount.currency] unsupported currency");
        Assert.isTrue(rates.getRates().containsKey(targetCurrency), "[targetCurrency] unsupported currency");
        BigDecimal value = amount.getAmount().multiply(rates.getRates().get(targetCurrency))
                .divide(rates.getRates().get(amount.getCurrency()), RoundingMode.HALF_UP);
        return new MoneyAmount(value, targetCurrency);
    }
}
