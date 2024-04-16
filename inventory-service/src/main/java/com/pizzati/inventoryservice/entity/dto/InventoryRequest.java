package com.pizzati.inventoryservice.entity.dto;

public record InventoryRequest(
        String skuCode,
        Integer quantity
) {
}
