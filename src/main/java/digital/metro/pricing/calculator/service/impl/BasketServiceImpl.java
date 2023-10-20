package digital.metro.pricing.calculator.service.impl;

import digital.metro.pricing.calculator.dto.BasketTotalsDTO;
import digital.metro.pricing.calculator.model.Basket;
import digital.metro.pricing.calculator.model.BasketEntry;
import digital.metro.pricing.calculator.service.ArticleService;
import digital.metro.pricing.calculator.service.BasketService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class BasketServiceImpl implements BasketService {

    private final ArticleService articleService;

    public BasketServiceImpl(ArticleService articleService) {
        this.articleService = articleService;
    }

    public BasketTotalsDTO calculateTotal(Basket basket) {
        Map<String, BigDecimal> pricedArticles = basket.getEntries().stream()
                .collect(Collectors.toMap(BasketEntry::getArticleId,
                        entry -> calculateEntryTotal(entry, basket.getCustomerId())));

        BigDecimal totalAmount = pricedArticles.values().stream()
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new BasketTotalsDTO(basket.getCustomerId(), pricedArticles, totalAmount);
    }

    public BigDecimal calculateEntryTotal(BasketEntry entry, String customerId) {
        BigDecimal quantity = entry.getQuantity();
        BigDecimal price = articleService.getArticlePrice(customerId, entry.getArticleId());

        return price.multiply(quantity);
    }
}
