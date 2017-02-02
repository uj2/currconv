package org.balaguta.currconv.data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

public interface ExchangeRatesSource {
    Map<String, BigDecimal> getLatestRates();
    Map<String, BigDecimal> getHistoricalRates(LocalDate ratesFrom);
}
