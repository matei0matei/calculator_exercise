package digital.metro.pricing.calculator.dto;

import java.math.BigDecimal;
import java.util.Map;

public class BasketCalculationResultDTO {

    private final String customerId;
    private final Map<String, BigDecimal> pricedEntries;
    private final BigDecimal totalAmount;

    public BasketCalculationResultDTO(String customerId, Map<String, BigDecimal> pricedEntries, BigDecimal totalAmount) {
        this.customerId = customerId;
        this.pricedEntries = pricedEntries;
        this.totalAmount = totalAmount;
    }

    public String getCustomerId() {
        return customerId;
    }

    public Map<String, BigDecimal> getPricedEntries() {
        return Map.copyOf(pricedEntries);
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }
}
