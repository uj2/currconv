package org.balaguta.currconv.data.entity;

import lombok.*;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public class MoneyAmount {
    private final BigDecimal amount;
    private final String currency;

    public String toString() {
        return amount + " " + currency;
    }
}
