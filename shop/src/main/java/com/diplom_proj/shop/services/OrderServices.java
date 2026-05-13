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
    private final CartRepository cartRepository;
    private final CartItemsRepository cartItemsRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final AddressRepository addressRepository;

    public OrderServices(UsersRepository usersRepository, CartRepository cartRepository,
                         CartItemsRepository cartItemsRepository, UsersService usersService,
                         AddressRepository addressRepository, OrderItemRepository orderItemRepository, OrderRepository orderRepository) {
        this.usersRepository = usersRepository;
        this.cartRepository = cartRepository;
        this.cartItemsRepository = cartItemsRepository;
        this.usersService = usersService;
        this.addressRepository = addressRepository;
        this.orderItemRepository = orderItemRepository;
        this.orderRepository = orderRepository;
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
        // Создание сущности Address
        Address newAddress = new Address();

        newAddress.setUser(user);
        newAddress.setName(request.getFirstName());
        newAddress.setSurname(request.getLastName());
        newAddress.setCountryAddress(request.getCountry());
        newAddress.setCityAddress(request.getCity());
        newAddress.setStreetAddress(request.getStreet());
        newAddress.setHouseNumberAddress(request.getBuildingNo());
        newAddress.setApartmentNumberAddress(request.getAptNo());
        newAddress.setCode(request.getZipCode());

        // Сохранение в базу
        addressRepository.save(newAddress);


        Carts cart = cartRepository.getCartsByUser_UserId(user.getUserId());

        // Получение товаров из корзины пользователя
        List<CartItems> cartItems = cartItemsRepository.findAllByCarts_CartsId(cart.getCartsId());
        System.out.println("ORDERS");
        System.out.println(cartItems);
        if (cartItems.isEmpty()) {
            throw new RuntimeException("Невозможно оформить заказ: корзина пуста.");
        }

        // Сохранение заказа
        Orders order = new Orders();
        // Подсчёт общей суммы заказа
        int totalAmount = cartItems.stream()
                .mapToInt(item -> item.getProduct().getProductPrice() * item.getQuantityProductInCartItem())
                .sum();

        order.setUsers(user);
        order.setOrderDate(String.valueOf(LocalDateTime.now()));
        order.setOrderStatus("CREATED");
        order.setTotalAmount(totalAmount);
        order.setAddress(newAddress); // Привязка сохранённого адреса
        order.setCity(request.getCity());

        String fullStreet = request.getStreet() + " " + request.getBuildingNo() + //Street + BuildingNumber(BuildingNo) + Apartment numbe(AptNo)
                (request.getAptNo() != null && !request.getAptNo().isEmpty() ? "/" + request.getAptNo() : "");
        order.setStreet(fullStreet);
        order.setIndex(request.getZipCode());

        order = orderRepository.save(order);

        // Перенос всех товароы с их колличеством в  OrderItems
        OrderItems orderItem = new OrderItems();

        for (CartItems item : cartItems) {
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
