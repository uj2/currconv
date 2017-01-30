package org.balaguta.currconv.data;

import org.balaguta.currconv.data.entity.MoneyAmount;

public interface Converter {
    MoneyAmount convert(MoneyAmount amount, String targetCurrency);
}
