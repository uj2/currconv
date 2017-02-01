package org.balaguta.currconv.data.impl;

import org.balaguta.currconv.app.CurrconvProperties;
import org.balaguta.currconv.data.ExchangeRatesSource;
import org.balaguta.currconv.data.entity.OpenExchangeRatesLatest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;
import org.springframework.web.client.RestOperations;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.net.URI;
import java.util.Map;

@Repository
public class OpenExchangeRatesSource implements ExchangeRatesSource {
    private static final String APP_ID_PARAM = "app_id";

    private final RestOperations restOperations;
    private final CurrconvProperties properties;

    @Autowired
    public OpenExchangeRatesSource(RestOperations restOperations, CurrconvProperties properties) {
        this.restOperations = restOperations;
        this.properties = properties;

        Assert.notNull(this.restOperations, "[restOperations] required; must not be null");
        Assert.notNull(this.properties, "[properties] required; must not be null");
    }

    @Override
    @Cacheable("conversions")
    public Map<String, BigDecimal> getLatestRates() {
        URI uri = UriComponentsBuilder.fromUri(properties.getOpenExchangeRates().getUrl())
                .queryParam(APP_ID_PARAM, properties.getOpenExchangeRates().getAppId())
                .build()
                .toUri();
        OpenExchangeRatesLatest rates = restOperations.getForObject(uri, OpenExchangeRatesLatest.class);
        return rates.getRates();
    }
}
