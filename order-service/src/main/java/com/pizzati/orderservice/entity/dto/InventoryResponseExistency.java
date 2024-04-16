package com.pizzati.orderservice.entity.dto;

public record InventoryResponseExistency(
        String skuCode,
        boolean inStock
) {
}
