package org.balaguta.currconv.service.impl;

import org.balaguta.currconv.app.CurrconvProperties;
import org.balaguta.currconv.service.ConversionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class ConversionServiceImpl implements ConversionService {

    private final List<String> supportedCurrencies;

    @Autowired
    public ConversionServiceImpl(CurrconvProperties properties) {
        List<String> supportedCurrencies = new ArrayList<>(properties.getSupportedCurrencies());
        Collections.sort(supportedCurrencies);
        this.supportedCurrencies = Collections.unmodifiableList(supportedCurrencies);

    }

    @Override
    public List<String> getSupportedCurrencies() {
        return supportedCurrencies;
    }
}
