package org.balaguta.currconv.data.entity;

import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Map;

@Data
@ToString
public class OpenExchangeRatesLatest {
    private long timestamp;
    private String base;
    private Map<String, BigDecimal> rates;
}
