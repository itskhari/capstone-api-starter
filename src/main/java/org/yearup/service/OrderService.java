package org.yearup.service;

import org.springframework.stereotype.Service;
import org.yearup.models.Order;
import org.yearup.models.OrderLineItems;
import org.yearup.models.ShoppingCart;
import org.yearup.models.ShoppingCartItem;
import org.yearup.repository.OrderLineRepository;
import org.yearup.repository.OrderRepository;

import java.time.LocalDateTime;
import java.util.Map;

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

        Map<Integer, ShoppingCartItem> cartItems = cart.getItems();

        Order order = new Order();
        order.setUserId(userId);
        order.setOrderDate(LocalDateTime.now());
        order.setTotal(cart.getTotal());

        Order savedOrder = orderRepository.save(order);

        for (ShoppingCartItem item : cartItems.values()) {
            OrderLineItems line = new OrderLineItems();
            line.setOrderId(savedOrder.getOrderId());
            line.setProductId(item.getProduct().getProductId());
            line.setQuantity(item.getQuantity());
            line.setPrice(item.getProduct().getPrice());

            orderLineRepository.save(line);
        }

        shoppingCartService.clearCart(userId);

        return savedOrder;
    }


}

