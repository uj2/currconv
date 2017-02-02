package org.balaguta.currconv.service;

import org.balaguta.currconv.data.entity.Conversion;
import org.balaguta.currconv.data.entity.MoneyAmount;

import java.time.LocalDate;
import java.util.List;

public interface ConversionService {
    List<String> getSupportedCurrencies();
    List<Conversion> getHistory();
    Conversion convert(MoneyAmount amount, String targetCurrency);
    Conversion convert(MoneyAmount amount, String targetCurrency, LocalDate ratesFrom);
}
