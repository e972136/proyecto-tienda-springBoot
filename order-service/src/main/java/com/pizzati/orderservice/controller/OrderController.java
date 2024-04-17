package com.pizzati.orderservice.controller;

import com.pizzati.orderservice.entity.dto.OrderReponse;
import com.pizzati.orderservice.entity.dto.OrderRequest;
import com.pizzati.orderservice.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String placeOrder(
            @RequestBody OrderRequest orderRequest
    ){
        orderService.placeOrder(orderRequest);
        return "Order Placed Successfully";
    }

    @GetMapping("/list")
    @ResponseStatus(HttpStatus.OK)
    public List<OrderReponse> getOrders(){
        return orderService.findAll();
    }
}
