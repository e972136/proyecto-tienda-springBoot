package com.pizzati.orderservice.entity.dto;

import com.pizzati.orderservice.entity.OrderLineItems;

import java.util.List;

public record OrderReponse(
        Integer id,
        String orderNumber,
        List<OrderLineItems> orderLineItemsList
) {
}
