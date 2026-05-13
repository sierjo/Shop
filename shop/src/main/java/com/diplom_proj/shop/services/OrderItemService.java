package com.diplom_proj.shop.services;

import com.diplom_proj.shop.entity.CartItems;
import com.diplom_proj.shop.entity.OrderItems;
import com.diplom_proj.shop.entity.Orders;
import com.diplom_proj.shop.repository.CartItemsRepository;
import com.diplom_proj.shop.repository.OrderItemRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderItemService {

    private final CartItemsRepository cartItemsRepository;
    private final OrderItemRepository orderItemRepository;

    public OrderItemService(CartItemsRepository cartItemsRepository, OrderItemRepository orderItemRepository) {
        this.cartItemsRepository = cartItemsRepository;
        this.orderItemRepository = orderItemRepository;
    }

    @Transactional
    public void createOrderItem(Orders order, List<CartItems> cartItems) {
        // Перенос всех товароы с их колличеством в  OrderItems
        for (CartItems item : cartItems) {
            OrderItems orderItem = new OrderItems();

            orderItem.setOrders(order);
            orderItem.setProducts(item.getProduct());
            orderItem.setQuantityInOrder(item.getQuantityProductInCartItem());
            orderItem.setPurchasePrice(item.getProduct().getProductPrice()); // Фиксацыя цены покупки

            orderItemRepository.save(orderItem);
        }
        // очистка карзины после заказа
        cartItemsRepository.deleteAll(cartItems);
    }
}
