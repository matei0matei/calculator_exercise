package digital.metro.pricing.calculator.service;

import digital.metro.pricing.calculator.model.Basket;
import digital.metro.pricing.calculator.dto.BasketTotalsDTO;
import digital.metro.pricing.calculator.model.BasketEntry;
import digital.metro.pricing.calculator.repository.PriceRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class BasketService {

    private final PriceRepository priceRepository;

    public BasketService(PriceRepository priceRepository) {
        this.priceRepository = priceRepository;
    }

    public BasketTotalsDTO calculateTotal(Basket basket) {
        Map<String, BigDecimal> pricedArticles = basket.getEntries().stream()
                .collect(Collectors.toMap(BasketEntry::getArticleId,
                        entry -> calculateEntryTotal(entry, basket.getCustomerId())));

        BigDecimal totalAmount = pricedArticles.values().stream()
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new BasketTotalsDTO(basket.getCustomerId(), pricedArticles, totalAmount);
    }

    public BigDecimal getArticlePrice(String customerId, String articleId) {
        if (customerId != null) {
            BigDecimal customerPrice = priceRepository.getPriceByArticleIdAndCustomerId(articleId, customerId);
            if (customerPrice != null) {
                return customerPrice;
            }
        }
        return priceRepository.getPriceByArticleId(articleId);
    }

    public BigDecimal calculateEntryTotal(BasketEntry entry, String customerId) {
        BigDecimal quantity = entry.getQuantity();
        BigDecimal price = getArticlePrice(customerId, entry.getArticleId());

        return price.multiply(quantity);
    }
}
