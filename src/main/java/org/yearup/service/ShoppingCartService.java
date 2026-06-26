package org.yearup.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yearup.models.CartItem;
import org.yearup.models.Product;
import org.yearup.models.ShoppingCart;
import org.yearup.models.ShoppingCartItem;
import org.yearup.repository.ShoppingCartRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ShoppingCartService
{
    // a shopping cart is built from cart rows plus a product lookup for each row
    private final ShoppingCartRepository shoppingCartRepository;
    private final ProductService productService;

    public ShoppingCartService(ShoppingCartRepository shoppingCartRepository, ProductService productService)
    {
        this.shoppingCartRepository = shoppingCartRepository;
        this.productService = productService;
    }

    // add additional methods here

    public ShoppingCart getByUserId(int userId)
    {
        // load the user's cart rows, look up each product, and build the ShoppingCart
            List<CartItem> items = shoppingCartRepository.findByUserId(userId);

            Map<Integer, ShoppingCartItem> cartItems = new HashMap<>();

            for (CartItem item : items) {
                Product product = productService.getById(item.getProductId());
                cartItems.put(item.getProductId(), new ShoppingCartItem(product, item.getQuantity()));
            }

            return new ShoppingCart(cartItems);
    }

    public ShoppingCart addProduct(int userId , int productId)
    {
        CartItem existing = shoppingCartRepository.findByUserIdAndProductId(userId, productId);

        if (existing == null) {
            CartItem newItem = new CartItem(userId, productId, 1);
            shoppingCartRepository.save(newItem);
        } else {
            existing.setQuantity(existing.getQuantity() + 1);
            shoppingCartRepository.save(existing);
        }

        return getByUserId(userId);

    }

    public ShoppingCart updateQuantity(int userId, int productId, int quantity)
    {
        CartItem item = shoppingCartRepository.findByUserIdAndProductId(userId, productId);

        if (item == null) {
            throw new RuntimeException("Product not in cart");
        }

        item.setQuantity(quantity);
        shoppingCartRepository.save(item);

        return getByUserId(userId);
    }

    @Transactional
    public ShoppingCart clearCart(int userId)
    {
        shoppingCartRepository.deleteByUserId(userId);

        return getByUserId(userId);
    }



}
