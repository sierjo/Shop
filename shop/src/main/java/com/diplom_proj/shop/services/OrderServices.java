package com.diplom_proj.shop.services;

import com.diplom_proj.shop.dto.OrderRequestDTO;
import com.diplom_proj.shop.dto.ProductDTO;
import com.diplom_proj.shop.entity.*;
import com.diplom_proj.shop.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderServices {
    private final UsersRepository usersRepository;
    private final UsersService usersService;
    private final AddressServices addressServices;
    private final CartRepository cartRepository;
    private final CartItemsRepository cartItemsRepository;
    private final OrderRepository orderRepository;
    private final OrderItemService orderItemService;

    public OrderServices(UsersRepository usersRepository, CartRepository cartRepository,
                         CartItemsRepository cartItemsRepository, UsersService usersService,
                         AddressServices addressServices,
                         OrderRepository orderRepository, OrderItemService orderItemService) {
        this.usersRepository = usersRepository;
        this.cartRepository = cartRepository;
        this.cartItemsRepository = cartItemsRepository;
        this.usersService = usersService;
        this.addressServices = addressServices;
        this.orderRepository = orderRepository;
        this.orderItemService = orderItemService;
    }

    public ProductDTO dtoProdPrice() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Users user = usersRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));
        ProductDTO productDTO = new ProductDTO();

        productDTO.setSumAllProductPrice(cartItemsRepository.getSumOfPricesByCartId(cartRepository.findCartsByUser_UserId(user.getUserId()).getCartsId()));
        System.out.println(productDTO);
        return productDTO;
    }

    @Transactional
    public void createOrder(OrderRequestDTO request) {
        Users user = usersService.getCurrentUser();
        Carts cart = cartRepository.getCartsByUser_UserId(user.getUserId());

        // Получение товаров из корзины пользователя
        List<CartItems> userCartItems = cartItemsRepository.findAllByCarts_CartsId(cart.getCartsId());

        if (userCartItems.isEmpty()) {
            throw new RuntimeException("Невозможно оформить заказ: корзина пуста.");
        }
        Address address = addressServices.saveAddress(request);

        // Сохранение заказа
        Orders order = new Orders();
        // Подсчёт общей суммы заказа
        int totalAmount = userCartItems.stream()
                .mapToInt(item -> item.getProduct().getProductPrice() * item.getQuantityProductInCartItem())
                .sum();

        order.setUsers(user);
        order.setOrderDate(String.valueOf(LocalDateTime.now()));
        order.setOrderStatus("CREATED");
        order.setTotalAmount(totalAmount);
        order.setAddress(address); // Привязка сохранённого адреса
        order.setCity(request.getCity());

        String fullStreet = request.getStreet() + " " + request.getBuildingNo() + //Street + BuildingNumber(BuildingNo) + Apartment numbe(AptNo)
                (request.getAptNo() != null && !request.getAptNo().isEmpty() ? "/" + request.getAptNo() : "");
        order.setStreet(fullStreet);
        order.setIndex(request.getZipCode());

        // Сохранение заказа и получение для id ордера для orderItem
        Orders savedOrder = orderRepository.save(order);

        // ПЕРЕДАЧА ЗАКАЗОВ
        orderItemService.createOrderItem(savedOrder, userCartItems);
    }
}
