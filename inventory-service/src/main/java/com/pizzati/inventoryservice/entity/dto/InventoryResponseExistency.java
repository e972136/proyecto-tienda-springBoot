package com.pizzati.inventoryservice.entity.dto;

public record InventoryResponseExistency(
        String skuCode,
        boolean inStock
) {
}
