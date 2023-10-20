package digital.metro.pricing.calculator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class BasketCalculatorService {

    private PriceRepository priceRepository;

    @Autowired
    public BasketCalculatorService(PriceRepository priceRepository) {
        this.priceRepository = priceRepository;
    }

    public BasketCalculationResult calculateBasket(Basket basket) {
        Map<String, BigDecimal> pricedArticles = basket.getEntries().stream()
                .collect(Collectors.toMap(
                        BasketEntry::getArticleId,
                        entry -> calculateArticle(entry, basket.getCustomerId())));

        BigDecimal totalAmount = pricedArticles.values().stream()
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new BasketCalculationResult(basket.getCustomerId(), pricedArticles, totalAmount);
    }

    public BigDecimal calculateArticle(BasketEntry entry, String customerId) {
        String articleId = entry.getArticleId();
        BigDecimal quantity = entry.getQuantity();

        if (customerId != null) {
            BigDecimal customerPrice = priceRepository.getPriceByArticleIdAndCustomerId(articleId, customerId);
            if (customerPrice != null) {
                return customerPrice.multiply(quantity);
            }
        }
        return priceRepository.getPriceByArticleId(articleId).multiply(quantity);
    }
}
