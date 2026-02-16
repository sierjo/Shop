package com.diplom_proj.shop.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Integer orderId;
    @Column(name = "order_date", updatable = false)
    @CreationTimestamp  // Аннотация говорит, что Hibernate сам поставит текущее время
    @JsonFormat(pattern = "dd-MM-yy")
    private String orderDate;
    @Column(name = "status")
    private String orderStatus;
    @Column(name = "total_amount")
    private Integer totalAmount; // Общая сумма заказа

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private Users users;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "adress_id", nullable = false)
    private Address address;

    @OneToMany(targetEntity = OrderItems.class, mappedBy = "orders", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItems> orderItems = new ArrayList<>();

    //  Адрес доставки ( Город, Улица, Почтовый индекс )
    @Column(name = "shipping_city")
    private String city;
    @Column(name = "shipping_street")
    private String street;
    @Column(name = "shipping_postal_code")
    private String index;

    public Orders() {
    }


}

