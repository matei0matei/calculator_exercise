package digital.metro.pricing.calculator.resource;

import digital.metro.pricing.calculator.dto.BasketTotalsDTO;
import digital.metro.pricing.calculator.model.Basket;
import digital.metro.pricing.calculator.service.BasketService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/basket")
public class BasketResource {

    private final BasketService basketService;

    public BasketResource(BasketService basketService) {
        this.basketService = basketService;
    }

    @PostMapping("/total")
    public BasketTotalsDTO calculateTotal(@RequestBody Basket basket) {
        return basketService.calculateTotal(basket);
    }

}
