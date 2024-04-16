package com.pizzati.inventoryservice.entity.dto;

public record InventoryResponse(
        Integer id,
        String skuCode,
        Integer quantity
) {
}
