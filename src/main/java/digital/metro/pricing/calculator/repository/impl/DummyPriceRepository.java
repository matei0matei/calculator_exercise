package digital.metro.pricing.calculator.repository.impl;

import digital.metro.pricing.calculator.repository.PriceRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

/**
 * A dummy implementation for testing purposes. In production, we would get real prices from a database.
 */
@Repository
public class DummyPriceRepository implements PriceRepository {

    private Map<String, BigDecimal> prices = new HashMap<>();
    private Random random = new Random();

    public BigDecimal getPriceByArticleId(String articleId) {
        return prices.computeIfAbsent(articleId,
                key -> BigDecimal.valueOf(0.5d + random.nextDouble() * 29.50d).setScale(2, RoundingMode.HALF_UP));
    }

    public Optional<BigDecimal> getPriceByArticleIdAndCustomerId(String articleId, String customerId) {
        return switch (customerId) {
            case "customer-1" -> getCustomPrice(articleId, new BigDecimal("0.90"));
            case "customer-2" -> getCustomPrice(articleId, new BigDecimal("0.85"));
            default -> Optional.empty();
        };
    }

    private Optional<BigDecimal> getCustomPrice(String articleId, BigDecimal factor) {
        return Optional.of(getPriceByArticleId(articleId).multiply(factor).setScale(2, RoundingMode.HALF_UP));
    }
}
