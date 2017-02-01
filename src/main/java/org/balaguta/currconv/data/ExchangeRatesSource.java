package org.balaguta.currconv.data;

import java.math.BigDecimal;
import java.util.Map;

public interface ExchangeRatesSource {
    Map<String, BigDecimal> getLatestRates();
}
