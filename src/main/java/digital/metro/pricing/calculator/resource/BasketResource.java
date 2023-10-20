package digital.metro.pricing.calculator.resource;

import digital.metro.pricing.calculator.dto.BasketTotalsDTO;
import digital.metro.pricing.calculator.model.Basket;
import digital.metro.pricing.calculator.service.BasketService;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/calculator")
public class BasketResource {

    private final BasketService basketService;

    public BasketResource(BasketService basketService) {
        this.basketService = basketService;
    }

    @PostMapping("/basket")
    public BasketTotalsDTO calculateBasket(@RequestBody Basket basket) {
        return basketService.calculateTotal(basket);
    }

    @GetMapping("/article/{articleId}")
    public BigDecimal getArticlePrice(@PathVariable String articleId,
                                      @RequestParam(value = "customerId", required = false) String customerId) {
        return basketService.getArticlePrice(customerId, articleId);
    }
}
