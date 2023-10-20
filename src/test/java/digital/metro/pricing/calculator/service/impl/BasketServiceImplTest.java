package digital.metro.pricing.calculator.service.impl;

import digital.metro.pricing.calculator.dto.BasketTotalsDTO;
import digital.metro.pricing.calculator.model.Basket;
import digital.metro.pricing.calculator.model.BasketEntry;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;

public class BasketServiceImplTest {

    @Mock
    private ArticleServiceImpl mockArticleService;

    private BasketServiceImpl service;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
        service = new BasketServiceImpl(mockArticleService);
    }

    @Test
    public void testCalculateEntryTotalWithOneProduct() {
        // GIVEN
        String customerId = "customer-1";
        String articleId = "article-1";
        BigDecimal price = new BigDecimal("34.29");
        Mockito.when(mockArticleService.getArticlePrice(customerId, articleId)).thenReturn(price);

        // WHEN
        BigDecimal result = service.calculateEntryTotal(new BasketEntry(articleId, BigDecimal.ONE), customerId);

        // THEN
        Assertions.assertThat(result).isEqualByComparingTo(price);
    }

    @Test
    public void testCalculateEntryTotalWithMoreProducts() {
        // GIVEN
        String customerId = "customer-1";
        String articleId = "article-1";
        BigDecimal price = new BigDecimal("34.29");
        BigDecimal quantity = BigDecimal.TEN;
        Mockito.when(mockArticleService.getArticlePrice(customerId, articleId)).thenReturn(price);

        // WHEN
        BigDecimal result = service.calculateEntryTotal(new BasketEntry(articleId, quantity), customerId);

        // THEN
        Assertions.assertThat(result).isEqualByComparingTo(price.multiply(quantity));
    }

    @Test
    public void testCalculateBasket() {
        // GIVEN
        String customerId = "customer-1";
        Basket basket = new Basket(customerId, Set.of(
                new BasketEntry("article-1", BigDecimal.ONE),
                new BasketEntry("article-2", BigDecimal.ONE),
                new BasketEntry("article-3", BigDecimal.ONE)));

        Map<String, BigDecimal> prices = Map.of(
                "article-1", new BigDecimal("1.50"),
                "article-2", new BigDecimal("0.29"),
                "article-3", new BigDecimal("9.99"));

        Mockito.when(mockArticleService.getArticlePrice(customerId, "article-1")).thenReturn(prices.get("article-1"));
        Mockito.when(mockArticleService.getArticlePrice(customerId, "article-2")).thenReturn(prices.get("article-2"));
        Mockito.when(mockArticleService.getArticlePrice(customerId, "article-3")).thenReturn(prices.get("article-3"));

        // WHEN
        BasketTotalsDTO result = service.calculateTotal(basket);

        // THEN
        Assertions.assertThat(result.customerId()).isEqualTo(customerId);
        Assertions.assertThat(result.pricedEntries()).isEqualTo(prices);
        Assertions.assertThat(result.totalAmount()).isEqualByComparingTo(new BigDecimal("11.78"));
    }
}
