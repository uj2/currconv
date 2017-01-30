package org.balaguta.currconv.data.entity;

import lombok.*;

import javax.persistence.Embeddable;
import java.math.BigDecimal;

@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@Embeddable
public class MoneyAmount {
    private final BigDecimal amount;
    private final String currency;

    public String toString() {
        return amount + " " + currency;
    }
}
