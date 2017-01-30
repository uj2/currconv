package org.balaguta.currconv.web;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class ConversionDto {
    @Min(0)
    @NotNull
    private BigDecimal amount;
    @NotNull
    private String fromCurrency;
    @NotNull
    private String toCurrency;

}
