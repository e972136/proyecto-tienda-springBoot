package com.pizzati.inventoryservice.service;

import com.pizzati.inventoryservice.entity.Inventory;
import com.pizzati.inventoryservice.entity.dto.InventoryRequest;
import com.pizzati.inventoryservice.entity.dto.InventoryResponse;
import com.pizzati.inventoryservice.entity.dto.InventoryResponseExistency;
import com.pizzati.inventoryservice.repository.InventroyRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.beans.Transient;
import java.util.List;

@Service
public class InventoryService {
    private final InventroyRepository inventroyRepository;

    public InventoryService(InventroyRepository inventroyRepository) {
        this.inventroyRepository = inventroyRepository;
    }

    @Transactional(readOnly = true)
    public boolean findInStock(String skuCode) {
        return inventroyRepository.findBySkuCode(skuCode).isPresent();
    }

    public InventoryResponse postInventory(InventoryRequest inventoryRequest) {
        Inventory inventory = new Inventory();
        inventory.setQuantity(inventoryRequest.quantity());
        inventory.setSkuCode(inventoryRequest.skuCode());
        inventory = inventroyRepository.save(inventory);
        return new InventoryResponse(
                inventory.getId(),
                inventory.getSkuCode(),
                inventory.getQuantity()
        );
    }

    public List<InventoryResponseExistency> findInStocks(List<String> skuCodes) {
        return inventroyRepository.findBySkuCodeIn(skuCodes).stream().map(i -> mapToResponse(i)).toList();
    }

    private InventoryResponseExistency mapToResponse(Inventory i) {
        return new InventoryResponseExistency(
                i.getSkuCode(),
                i.getQuantity()>0
        );
    }
}
