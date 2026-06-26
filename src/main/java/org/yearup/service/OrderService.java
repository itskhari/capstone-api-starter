package org.yearup.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yearup.models.*;
import org.yearup.repository.OrderLineRepository;
import org.yearup.repository.OrderRepository;
import org.yearup.repository.ProfileRepository;

import java.time.LocalDateTime;
import java.util.Map;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderLineRepository orderLineRepository;
    private final ShoppingCartService shoppingCartService;
    private final ProfileRepository profileRepository;

    public OrderService(OrderRepository orderRepository, OrderLineRepository orderLineRepository, ShoppingCartService shoppingCartService, ProfileRepository profileRepository) {
        this.orderRepository = orderRepository;
        this.orderLineRepository = orderLineRepository;
        this.shoppingCartService = shoppingCartService;
        this.profileRepository = profileRepository;
    }

    @Transactional
    public Order createOrder(int userId) {
        ShoppingCart cart = shoppingCartService.getByUserId(userId);

        Map<Integer, ShoppingCartItem> cartItems = cart.getItems();

        Profile profile = profileRepository.findByUserId(userId);
        if (profile == null) {
            throw new RuntimeException("User profile not found for checkout");
        }

        Order order = new Order();
        order.setUserId(userId);
        order.setOrderDate(LocalDateTime.now());
        order.setTotal(cart.getTotal());
        order.setAddress(profile.getAddress());
        order.setCity(profile.getCity());
        order.setState(profile.getState());
        order.setZip(profile.getZip());

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

