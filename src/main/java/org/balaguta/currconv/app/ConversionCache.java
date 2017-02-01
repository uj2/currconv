package org.balaguta.currconv.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.stereotype.Component;

import javax.cache.CacheManager;
import javax.cache.configuration.MutableConfiguration;
import javax.cache.expiry.CreatedExpiryPolicy;
import javax.cache.expiry.Duration;
import java.util.concurrent.TimeUnit;

@Component
public class ConversionCache implements JCacheManagerCustomizer {

    private final CurrconvProperties properties;

    @Autowired
    public ConversionCache(CurrconvProperties properties) {
        this.properties = properties;
    }

    @Override
    public void customize(CacheManager cacheManager) {

        MutableConfiguration<Object, Object> configuration = new MutableConfiguration<>();
        if (properties.getConversionCacheTtl() != null) {
            configuration.setExpiryPolicyFactory(CreatedExpiryPolicy.factoryOf(
                    new Duration(TimeUnit.SECONDS, properties.getConversionCacheTtl().getSeconds())));
        }
        configuration.setManagementEnabled(true);
        configuration.setStatisticsEnabled(true);

        cacheManager.createCache("conversions", configuration);
    }
}
