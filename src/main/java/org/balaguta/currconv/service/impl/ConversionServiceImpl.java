package org.balaguta.currconv.service.impl;

import org.balaguta.currconv.app.CurrconvProperties;
import org.balaguta.currconv.data.ConversionRepository;
import org.balaguta.currconv.data.Converter;
import org.balaguta.currconv.data.entity.Conversion;
import org.balaguta.currconv.data.entity.MoneyAmount;
import org.balaguta.currconv.service.ConversionService;
import org.balaguta.currconv.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class ConversionServiceImpl implements ConversionService {

    private final Converter converter;
    private final ConversionRepository conversionRepository;
    private final UserService userService;
    private final List<String> supportedCurrencies;

    @Autowired
    public ConversionServiceImpl(Converter converter,
                                 ConversionRepository conversionRepository,
                                 UserService userService,
                                 CurrconvProperties properties) {
        List<String> supportedCurrencies = new ArrayList<>(properties.getSupportedCurrencies());
        Collections.sort(supportedCurrencies);
        this.supportedCurrencies = Collections.unmodifiableList(supportedCurrencies);
        this.converter = converter;
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
        conversion.setTarget(converter.convert(amount, targetCurrency));
        conversion.setUser(userService.getCurrentUser());
        return conversionRepository.save(conversion);
    }

}
