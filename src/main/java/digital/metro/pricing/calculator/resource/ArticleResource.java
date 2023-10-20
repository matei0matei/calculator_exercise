package digital.metro.pricing.calculator.resource;

import digital.metro.pricing.calculator.service.ArticleService;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/article")
public class ArticleResource {

    private final ArticleService articleService;

    public ArticleResource(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/{articleId}/price")
    public BigDecimal getArticlePrice(@PathVariable String articleId,
                                      @RequestParam(value = "customerId", required = false) String customerId) {
        return articleService.getArticlePrice(customerId, articleId);
    }
}
