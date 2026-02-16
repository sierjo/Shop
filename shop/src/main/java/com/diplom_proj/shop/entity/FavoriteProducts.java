package com.diplom_proj.shop.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "favorite_products")
public class FavoriteProducts {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer favoriteProductsId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Products products;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private Users users;

    public FavoriteProducts() {
    }

    public FavoriteProducts(Integer favoriteProductsId, Products products, Users users) {
        this.favoriteProductsId = favoriteProductsId;
        this.products = products;
        this.users = users;
    }

    public Integer getFavoriteProductsId() {
        return favoriteProductsId;
    }

    public void setFavoriteProductsId(Integer favoriteProductsId) {
        this.favoriteProductsId = favoriteProductsId;
    }

    public Products getProducts() {
        return products;
    }

    public void setProducts(Products products) {
        this.products = products;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "FavoriteProducts{" +
                "favoriteProductsId=" + favoriteProductsId +
                ", products=" + products +
                ", users=" + users +
                '}';
    }
}
