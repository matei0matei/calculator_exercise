package digital.metro.pricing.calculator.service.impl;

import digital.metro.pricing.calculator.repository.PriceRepository;
import digital.metro.pricing.calculator.service.ArticleService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class ArticleServiceImpl implements ArticleService {

    private final PriceRepository priceRepository;

    public ArticleServiceImpl(PriceRepository priceRepository) {
        this.priceRepository = priceRepository;
    }

    public BigDecimal getArticlePrice(String customerId, String articleId) {
        if (customerId != null) {
            Optional<BigDecimal> customerPrice = priceRepository.getPriceByArticleIdAndCustomerId(articleId, customerId);
            if (customerPrice.isPresent()) {
                return customerPrice.get();
            }
        }
        return priceRepository.getPriceByArticleId(articleId);
    }
}
