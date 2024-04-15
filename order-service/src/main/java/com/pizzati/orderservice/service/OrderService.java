package com.pizzati.orderservice.service;

import com.pizzati.orderservice.entity.Order;
import com.pizzati.orderservice.entity.OrderLineItems;
import com.pizzati.orderservice.entity.dto.OrderLineItemsRequest;
import com.pizzati.orderservice.entity.dto.OrderRequest;
import com.pizzati.orderservice.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public void placeOrder(OrderRequest orderRequest){
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        List<OrderLineItems> list = orderRequest.orderLineItemsRequestList().stream().map(this::mapToOrderItem).toList();
        order.setOrderLineItemsList(list);
        orderRepository.save(order);
    }

    private OrderLineItems mapToOrderItem(OrderLineItemsRequest o) {
        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setSkuCode(o.skuCode());
        orderLineItems.setPrice(o.price());
        orderLineItems.setQuantity(o.quantity());
        return orderLineItems;
    }
}
