package digital.metro.pricing.calculator.service;

import java.math.BigDecimal;

public interface ArticleService {

    BigDecimal getArticlePrice(String customerId, String articleId);

}
