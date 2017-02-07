package org.balaguta.currconv.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Service;

@ManagedResource
@Service
public class JmxCurrencyConverter {

    private CurrconvProperties properties;
    private CacheManager cacheManager;

    @Autowired
    public JmxCurrencyConverter(CurrconvProperties properties, CacheManager cacheManager) {
        this.properties = properties;
        this.cacheManager = cacheManager;
    }

    @ManagedAttribute(description = "openexchangerates.org app-id")
    public String getAppId() {
        return properties.getOpenExchangeRates().getAppId();
    }

    @ManagedAttribute
    public void setAppId(String appId) {
        properties.getOpenExchangeRates().setAppId(appId);
    }

    @ManagedOperation
    public void clearCache() {
        cacheManager.getCache(RatesCache.CACHE_NAME).clear();
    }
}
