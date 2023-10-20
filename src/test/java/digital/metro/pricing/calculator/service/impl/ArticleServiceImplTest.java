package digital.metro.pricing.calculator.service.impl;

import digital.metro.pricing.calculator.repository.impl.DummyPriceRepository;
import digital.metro.pricing.calculator.service.ArticleService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Optional;

class ArticleServiceImplTest {

    @Mock
    private DummyPriceRepository mockPriceRepository;

    private ArticleService service;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
        service = new ArticleServiceImpl(mockPriceRepository);
    }

    @Test
    public void testGetDefaultArticlePrice() {
        // GIVEN
        String articleId = "article-1";
        BigDecimal standardPrice = new BigDecimal("34.29");

        Mockito.when(mockPriceRepository.getPriceByArticleId(articleId)).thenReturn(standardPrice);

        // WHEN
        BigDecimal result = service.getArticlePrice(null, articleId);

        // THEN
        Assertions.assertThat(result).isEqualByComparingTo(standardPrice);
    }

    @Test
    public void testGetCustomerArticlePrice() {
        // GIVEN
        String articleId = "article-1";
        String customerId = "customer-1";
        BigDecimal standardPrice = new BigDecimal("34.29");
        BigDecimal customerPrice = new BigDecimal("29.99");

        Mockito.when(mockPriceRepository.getPriceByArticleId(articleId)).thenReturn(standardPrice);
        Mockito.when(mockPriceRepository.getPriceByArticleIdAndCustomerId(articleId, customerId))
                .thenReturn(Optional.of(customerPrice));

        // WHEN
        BigDecimal result = service.getArticlePrice(customerId, articleId);

        // THEN
        Assertions.assertThat(result).isEqualByComparingTo(customerPrice);
    }

}