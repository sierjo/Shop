package com.diplom_proj.shop.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "carts")
public class Carts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer cartsId;//                                                            при удалении ↓ товара из корзины этот элемент автоматически удалиться из таблицы
    @OneToMany(targetEntity = CartsItem.class, mappedBy = "carts", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartsItem> cartsItems = new ArrayList<>();
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private Users user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Products products;

    public Carts() {
    }

    public Carts(Integer cartsId, List<CartsItem> cartsItems, Users user, Products products) {
        this.cartsId = cartsId;
        this.cartsItems = cartsItems;
        this.user = user;
        this.products = products;
    }

    public Integer getCartsId() {
        return cartsId;
    }

    public void setCartsId(Integer cartsId) {
        this.cartsId = cartsId;
    }

    public List<CartsItem> getCartsItems() {
        return cartsItems;
    }

    public void setCartsItems(List<CartsItem> cartsItems) {
        this.cartsItems = cartsItems;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public Products getProducts() {
        return products;
    }

    public void setProducts(Products products) {
        this.products = products;
    }

    @Override
    public String toString() {
        return "Carts{" +
                "cartsId=" + cartsId +
                ", cartsItems=" + cartsItems +
                ", user=" + user +
                ", products=" + products +
                '}';
    }
}
