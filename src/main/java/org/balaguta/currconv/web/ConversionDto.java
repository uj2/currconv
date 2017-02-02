package org.balaguta.currconv.web;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class ConversionDto {
    @Min(0)
    @NotNull
    private BigDecimal amount;
    @NotNull
    private String fromCurrency;
    @NotNull
    private String toCurrency;
    @NotNull
    @PastOrNow
    @DateTimeFormat(pattern = "dd/MMM/yyyy")
    private LocalDate ratesFrom;

}
