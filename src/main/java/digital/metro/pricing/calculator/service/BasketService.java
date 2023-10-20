package digital.metro.pricing.calculator.service;

import digital.metro.pricing.calculator.dto.BasketTotalsDTO;
import digital.metro.pricing.calculator.model.Basket;

public interface BasketService {

    BasketTotalsDTO calculateTotal(Basket basket);

}
