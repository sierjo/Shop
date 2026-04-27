package com.diplom_proj.shop.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "cart_items")
public class CartItems {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer cartItemId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id", nullable = false)
    private Carts carts;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_product_id", nullable = false)
    private Products product;

    @Column(name = "quantity")
    private Integer priceInCartItem;

    public CartItems() {
    }

    public CartItems(Integer cartItemId, Carts carts, Products product, Integer priceInCartItem) {
        this.cartItemId = cartItemId;
        this.carts = carts;
        this.product = product;
        this.priceInCartItem = priceInCartItem;
    }

    public Integer getCartItemId() {
        return cartItemId;
    }

    public void setCartItemId(Integer cartItemId) {
        this.cartItemId = cartItemId;
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

    public Integer getPriceInCartItem() {
        return priceInCartItem;
    }

    public void setPriceInCartItem(Integer priceInCartItem) {
        this.priceInCartItem = priceInCartItem;
    }

    @Override
    public String toString() {
        return "CartsItem{" +
                "cartItemId=" + cartItemId +
                ", carts=" + (carts != null ? carts.getCartsId() : "null")+
                ", productId=" + (product != null ? product.getProductId() : "null") +
                ", priceInCart=" + priceInCartItem +
                '}';
    }
}
