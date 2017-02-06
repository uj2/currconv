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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

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
        this.supportedCurrencies = sorted(properties.getSupportedCurrencies());
        this.ratesSource = ratesSource;
        this.conversionRepository = conversionRepository;
        this.userService = userService;
    }

    private List<String> sorted(Collection<String> input) {
        List<String> output = new ArrayList<>(input);
        Collections.sort(output);
        return Collections.unmodifiableList(output);
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
        return persistConversion(amount, convert(ratesSource.getLatestRates(), amount, targetCurrency), LocalDate.now());
    }

    @Override
    public Conversion convert(MoneyAmount amount, String targetCurrency, LocalDate ratesFrom) {
        return persistConversion(amount, convert(ratesSource.getHistoricalRates(ratesFrom), amount, targetCurrency),
                ratesFrom);
    }

    private Conversion persistConversion(MoneyAmount source, MoneyAmount target, LocalDate ratesFrom) {
        Conversion conversion = new Conversion();
        conversion.setTimestamp(LocalDateTime.now());
        conversion.setSource(source);
        conversion.setTarget(target);
        conversion.setRatesFrom(ratesFrom);
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
