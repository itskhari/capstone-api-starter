package org.yearup.service;

import org.springframework.stereotype.Service;
import org.yearup.models.Order;
import org.yearup.models.ShoppingCart;
import org.yearup.models.ShoppingCartItem;
import org.yearup.repository.OrderLineRepository;
import org.yearup.repository.OrderRepository;

import java.time.LocalDateTime;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderLineRepository orderLineRepository;
    private final ShoppingCartService shoppingCartService;

    public OrderService(OrderRepository orderRepository, OrderLineRepository orderLineRepository, ShoppingCartService shoppingCartService) {
        this.orderRepository = orderRepository;
        this.orderLineRepository = orderLineRepository;
        this.shoppingCartService = shoppingCartService;
    }

    public Order createOrder(int userId) {
        ShoppingCart cart = shoppingCartService.getByUserId(userId);

        Order order = new Order();
        order.setUserId(userId);
        order.setOrderDate(LocalDateTime.now());
        order.setShippingAmount(cart.getTotal());

        Order savedOrder = orderRepository.save(order);
    }
}
