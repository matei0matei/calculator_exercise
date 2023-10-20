package digital.metro.pricing.calculator.service;

import digital.metro.pricing.calculator.model.Basket;
import digital.metro.pricing.calculator.dto.BasketCalculationResultDTO;
import digital.metro.pricing.calculator.model.BasketEntry;
import digital.metro.pricing.calculator.repository.PriceRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class BasketCalculatorService {

    private final PriceRepository priceRepository;

    public BasketCalculatorService(PriceRepository priceRepository) {
        this.priceRepository = priceRepository;
    }

    public BasketCalculationResultDTO calculateBasketTotal(Basket basket) {
        Map<String, BigDecimal> pricedArticles = basket.getEntries().stream()
                .collect(Collectors.toMap(
                        BasketEntry::getArticleId,
                        entry -> calculateEntryTotal(entry, basket.getCustomerId())));

        BigDecimal totalAmount = pricedArticles.values().stream()
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new BasketCalculationResultDTO(basket.getCustomerId(), pricedArticles, totalAmount);
    }

    public BigDecimal calculateEntryTotal(BasketEntry entry, String customerId) {
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
