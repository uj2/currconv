package org.balaguta.currconv.service.impl;

import org.balaguta.currconv.app.CurrconvProperties;
import org.balaguta.currconv.data.ConversionRepository;
import org.balaguta.currconv.data.ExchangeRatesSource;
import org.balaguta.currconv.data.entity.Conversion;
import org.balaguta.currconv.data.entity.MoneyAmount;
import org.balaguta.currconv.service.ConversionService;
import org.balaguta.currconv.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class ConversionServiceImpl implements ConversionService {

    private final ExchangeRatesSource ratesSource;
    private final ConversionRepository conversionRepository;
    private final UserService userService;
    private final List<String> supportedCurrencies;

    @Autowired
    public ConversionServiceImpl(ExchangeRatesSource ratesSource,
                                 ConversionRepository conversionRepository,
                                 UserService userService,
                                 CurrconvProperties properties) {
        List<String> supportedCurrencies = new ArrayList<>(properties.getSupportedCurrencies());
        Collections.sort(supportedCurrencies);
        this.supportedCurrencies = Collections.unmodifiableList(supportedCurrencies);
        this.ratesSource = ratesSource;
        this.conversionRepository = conversionRepository;
        this.userService = userService;
    }

    @Override
    public List<String> getSupportedCurrencies() {
        return supportedCurrencies;
    }


    @Override
    @Transactional(readOnly = true)
    public List<Conversion> getHistory() {
        return conversionRepository.findFirst10ByUserOrderByTimestampDesc(userService.getCurrentUser());
    }

    @Override
    public Conversion convert(MoneyAmount amount, String targetCurrency) {
        Assert.isTrue(supportedCurrencies.contains(amount.getCurrency()),
                "[amount.currency] not supported");
        Assert.isTrue(supportedCurrencies.contains(targetCurrency),
                "[targetCurrency] not supported");
        Conversion conversion = new Conversion();
        conversion.setTimestamp(LocalDateTime.now());
        conversion.setSource(amount);
        conversion.setTarget(convert(ratesSource.getLatestRates(), amount, targetCurrency));
        conversion.setUser(userService.getCurrentUser());
        return conversionRepository.save(conversion);
    }

    private MoneyAmount convert(Map<String, BigDecimal> rates, MoneyAmount amount, String targetCurrency) {
        Assert.isTrue(rates.containsKey(amount.getCurrency()), "[amount.currency] unsupported currency");
        Assert.isTrue(rates.containsKey(targetCurrency), "[targetCurrency] unsupported currency");
        BigDecimal value = amount.getAmount().multiply(rates.get(targetCurrency))
                .divide(rates.get(amount.getCurrency()), RoundingMode.HALF_UP);
        return new MoneyAmount(value, targetCurrency);
    }

}
