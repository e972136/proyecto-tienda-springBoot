package com.pizzati.inventoryservice.controller;

import com.pizzati.inventoryservice.entity.dto.InventoryRequest;
import com.pizzati.inventoryservice.entity.dto.InventoryResponse;
import com.pizzati.inventoryservice.entity.dto.InventoryResponseExistency;
import com.pizzati.inventoryservice.service.InventoryService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {
    private final InventoryService inventoryService;


    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @GetMapping("/{skuCode}")
    @ResponseStatus(HttpStatus.OK)
    public boolean isInStock(
        @PathVariable String skuCode
    ){
        return inventoryService.findInStock(skuCode);
    }


    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryResponseExistency> allInStock(
            @RequestParam List<String> skuCodes
            ){
        return inventoryService.findInStocks(skuCodes);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public InventoryResponse postInventory(
            InventoryRequest inventoryRequest
    ){
        return inventoryService.postInventory(inventoryRequest);
    }
}
