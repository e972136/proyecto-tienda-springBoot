package com.pizzati.orderservice.entity.dto;

import java.util.List;

public record OrderRequest(
        List<OrderLineItemsRequest> orderLineItemsRequestList
) {
}
