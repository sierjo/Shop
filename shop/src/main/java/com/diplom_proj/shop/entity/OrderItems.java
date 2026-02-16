package com.diplom_proj.shop.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "order_items")
public class OrderItems {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer orderItemId;
    @Column(name = "quantity")
    private Integer quantityInOrder;
    @Column(name = "price_at_purchase")
    private Integer purchasePrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Orders orders;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Products products;

    public OrderItems() {
    }

    public OrderItems(Integer orderItemId, Integer quantityInOrder, Integer purchasePrice, Orders orders, Products products) {
        this.orderItemId = orderItemId;
        this.quantityInOrder = quantityInOrder;
        this.purchasePrice = purchasePrice;
        this.orders = orders;
        this.products = products;
    }

    public Integer getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(Integer orderItemId) {
        this.orderItemId = orderItemId;
    }

    public Integer getQuantityInOrder() {
        return quantityInOrder;
    }

    public void setQuantityInOrder(Integer quantityInOrder) {
        this.quantityInOrder = quantityInOrder;
    }

    public Integer getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(Integer purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public Orders getOrders() {
        return orders;
    }

    public void setOrders(Orders orders) {
        this.orders = orders;
    }

    public Products getProducts() {
        return products;
    }

    public void setProducts(Products products) {
        this.products = products;
    }

    @Override
    public String toString() {
        return "OrderItems{" +
                "orderItemId=" + orderItemId +
                ", quantityInOrder=" + quantityInOrder +
                ", purchasePrice=" + purchasePrice +
                ", orders=" + orders +
                ", products=" + products +
                '}';
    }
}
