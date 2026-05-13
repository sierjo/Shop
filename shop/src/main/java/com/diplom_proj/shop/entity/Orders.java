package com.diplom_proj.shop.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

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

    public Orders(String orderDate, String orderStatus, Integer totalAmount,
                  Users users, Address address, List<OrderItems> orderItems,
                  String city, String street, String index) {
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.totalAmount = totalAmount;
        this.users = users;
        this.address = address;
        this.orderItems = orderItems;
        this.city = city;
        this.street = street;
        this.index = index;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Integer getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Integer totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<OrderItems> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItems> orderItems) {
        this.orderItems = orderItems;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    @Override
    public String toString() {
        return "Orders{" +
                "orderId=" + orderId +
                ", orderDate=" + orderDate +
                ", orderStatus='" + orderStatus + '\'' +
                ", totalAmount=" + totalAmount +
                ", userId=" + (users != null ? users.getUserId() : "null") + // Берем только ID
                ", addressId=" + (address != null ? address.getAddressId() : "null") +
                ", orderItemsCount=" + (orderItems != null ? orderItems.size() : 0) + // Берем только количество товаров
                ", city='" + city + '\'' +
                ", street='" + street + '\'' +
                ", index='" + index + '\'' +
                '}';
    }
}

