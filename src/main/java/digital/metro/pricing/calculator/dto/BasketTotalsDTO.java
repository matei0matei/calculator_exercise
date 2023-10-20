package digital.metro.pricing.calculator.dto;

import java.math.BigDecimal;
import java.util.Map;

public record BasketTotalsDTO(String customerId, Map<String, BigDecimal> pricedEntries, BigDecimal totalAmount) {

    @Override
    public Map<String, BigDecimal> pricedEntries() {
        return Map.copyOf(pricedEntries);
    }
}
