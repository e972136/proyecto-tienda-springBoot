package com.pizzati.orderservice.service;

import com.pizzati.orderservice.entity.Order;
import com.pizzati.orderservice.entity.OrderLineItems;
import com.pizzati.orderservice.entity.dto.InventoryResponseExistency;
import com.pizzati.orderservice.entity.dto.OrderLineItemsRequest;
import com.pizzati.orderservice.entity.dto.OrderReponse;
import com.pizzati.orderservice.entity.dto.OrderRequest;
import com.pizzati.orderservice.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final WebClient webClient;

    public OrderService(OrderRepository orderRepository, WebClient webClient) {
        this.orderRepository = orderRepository;
        this.webClient = webClient;
    }

    public void placeOrder(OrderRequest orderRequest){
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        List<OrderLineItems> list = orderRequest.orderLineItemsRequestList().stream().map(this::mapToOrderItem).toList();
        order.setOrderLineItemsList(list);

        List<String> skuCodes = list.stream().map(o -> o.getSkuCode()).toList();

        //llamar a inventario y ver si hay producto en inventario
        InventoryResponseExistency[] result= webClient.get()
                .uri("http://localhost:9003/api/inventory",
                        uriBuilder -> uriBuilder.queryParam("skuCodes",skuCodes).build())
                .retrieve()
                .bodyToMono(InventoryResponseExistency[].class)
                .block();

        boolean allInStock = Arrays.stream(result).allMatch(a -> a.inStock());

        if(allInStock){
            orderRepository.save(order);
        }else{
            throw new IllegalArgumentException("Producto sin inventario");
        }

    }

    private OrderLineItems mapToOrderItem(OrderLineItemsRequest o) {
        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setSkuCode(o.skuCode());
        orderLineItems.setPrice(o.price());
        orderLineItems.setQuantity(o.quantity());
        return orderLineItems;
    }

    public List<OrderReponse> findAll() {
        return orderRepository.findAll().stream().map(o->mapToOrder(o)).toList();
    }

    private OrderReponse mapToOrder(Order o) {
        return new OrderReponse(
                o.getId(),
                o.getOrderNumber(),
                o.getOrderLineItemsList()
        );
    }
}
