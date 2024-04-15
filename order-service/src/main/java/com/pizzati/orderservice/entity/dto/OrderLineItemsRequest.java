package com.pizzati.orderservice.entity.dto;

import java.math.BigDecimal;

public record OrderLineItemsRequest(
         Integer id,
         String skuCode,
         BigDecimal price,
         Integer quantity
) {
}
