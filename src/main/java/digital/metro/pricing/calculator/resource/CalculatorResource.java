package digital.metro.pricing.calculator.resource;

import digital.metro.pricing.calculator.dto.BasketCalculationResultDTO;
import digital.metro.pricing.calculator.model.Basket;
import digital.metro.pricing.calculator.model.BasketEntry;
import digital.metro.pricing.calculator.service.BasketCalculatorService;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
public class CalculatorResource {

    private final BasketCalculatorService basketCalculatorService;

    public CalculatorResource(BasketCalculatorService basketCalculatorService) {
        this.basketCalculatorService = basketCalculatorService;
    }

    @PostMapping("/calculator/calculate-basket")
    public BasketCalculationResultDTO calculateBasket(@RequestBody Basket basket) {
        return basketCalculatorService.calculateBasketTotal(basket);
    }

    @GetMapping("/calculator/article/{articleId}")
    public BigDecimal getArticlePrice(@PathVariable String articleId) {
        return basketCalculatorService.calculateEntryTotal(new BasketEntry(articleId, BigDecimal.ONE), null);
    }

    @GetMapping("/calculator/getarticlepriceforcustomer")
    public BigDecimal getArticlePriceForCustomer(@RequestParam String articleId, @RequestParam String customerId) {
        return basketCalculatorService.calculateEntryTotal(new BasketEntry(articleId, BigDecimal.ONE), customerId);
    }
}
