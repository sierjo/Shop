package com.diplom_proj.shop.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "carts_item")
public class CartsItem {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer cartsItmId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id", nullable = false)
    private Carts carts;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_product_id", nullable = false)
    private Products product;

    @Column(name = "quantity")
    private Integer priceInCart;

    public CartsItem() {
    }

    public CartsItem(Integer cartsItmId, Carts carts, Products product, Integer priceInCart) {
        this.cartsItmId = cartsItmId;
        this.carts = carts;
        this.product = product;
        this.priceInCart = priceInCart;
    }

    public Integer getCartsItmId() {
        return cartsItmId;
    }

    public void setCartsItmId(Integer cartsItmId) {
        this.cartsItmId = cartsItmId;
    }

    public Carts getCarts() {
        return carts;
    }

    public void setCarts(Carts carts) {
        this.carts = carts;
    }

    public Products getProduct() {
        return product;
    }

    public void setProduct(Products product) {
        this.product = product;
    }

    public Integer getPriceInCart() {
        return priceInCart;
    }

    public void setPriceInCart(Integer priceInCart) {
        this.priceInCart = priceInCart;
    }

    @Override
    public String toString() {
        return "CartsItem{" +
                "cartsItmId=" + cartsItmId +
                ", carts=" + carts +
                ", product=" + product +
                ", priceInCart=" + priceInCart +
                '}';
    }
}
