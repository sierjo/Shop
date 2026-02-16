package com.diplom_proj.shop.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "products")
public class Products {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Integer productId;
    @Column(name = "product_name")
    private String productName;
    @Column(name = "description")
    private String productDescription;
    @Column(name = "product_price")
    private Integer productPrice;
    @Column(name = "stock_quantity")
    private Integer productQuantity;
    @Column(nullable = true, length = 64)
    private String productsPhoto;

    @OneToMany(targetEntity = CartsItem.class, mappedBy = "product", cascade = CascadeType.ALL)
    private List<CartsItem> cartsItems = new ArrayList<>();
    @OneToMany(targetEntity = OrderItems.class, mappedBy = "products", cascade = CascadeType.ALL)
    private List<OrderItems> orderItems = new ArrayList<>();
    @OneToMany(targetEntity = FavoriteProducts.class, mappedBy = "products", cascade = CascadeType.ALL)
    private List<FavoriteProducts> favoriteProducts = new ArrayList<>();

    public Products() {
    }

    public Products(Integer productId, String productName, String productDescription, Integer productPrice, Integer productQuantity, String productsPhoto, List<CartsItem> cartsItems, List<OrderItems> orderItems, List<FavoriteProducts> favoriteProducts) {
        this.productId = productId;
        this.productName = productName;
        this.productDescription = productDescription;
        this.productPrice = productPrice;
        this.productQuantity = productQuantity;
        this.productsPhoto = productsPhoto;
        this.cartsItems = cartsItems;
        this.orderItems = orderItems;
        this.favoriteProducts = favoriteProducts;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public Integer getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Integer productPrice) {
        this.productPrice = productPrice;
    }

    public Integer getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(Integer productQuantity) {
        this.productQuantity = productQuantity;
    }

    public String getProductsPhoto() {
        return productsPhoto;
    }

    public void setProductsPhoto(String productsPhoto) {
        this.productsPhoto = productsPhoto;
    }

    public List<CartsItem> getCartsItems() {
        return cartsItems;
    }

    public void setCartsItems(List<CartsItem> cartsItems) {
        this.cartsItems = cartsItems;
    }

    public List<OrderItems> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItems> orderItems) {
        this.orderItems = orderItems;
    }

    public List<FavoriteProducts> getFavoriteProducts() {
        return favoriteProducts;
    }

    public void setFavoriteProducts(List<FavoriteProducts> favoriteProducts) {
        this.favoriteProducts = favoriteProducts;
    }

    @Override
    public String toString() {
        return "Products{" +
                "productId=" + productId +
                ", productName='" + productName + '\'' +
                ", productDescription='" + productDescription + '\'' +
                ", productPrice=" + productPrice +
                ", productQuantity=" + productQuantity +
                ", productsPhoto='" + productsPhoto + '\'' +
                ", cartsItems=" + cartsItems +
                ", orderItems=" + orderItems +
                ", favoriteProducts=" + favoriteProducts +
                '}';
    }
}
