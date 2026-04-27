package com.diplom_proj.shop.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "carts")
public class Carts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer cartsId;//                                                            при удалении ↓ товара из корзины этот элемент автоматически удалиться из таблицы
//    @OneToMany(targetEntity = CartItems.class, mappedBy = "carts", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<CartItems> cartItems = new ArrayList<>();
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private Users user;

    @Column(name = "is_empty")
    private boolean isEmpty;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "product_id", nullable = false)
//    private Products products;

    public Carts() {
    }

    public Carts(Integer cartsId, Users user, boolean isEmpty) {
        this.cartsId = cartsId;
        this.user = user;
        this.isEmpty = isEmpty;
    }

    public Integer getCartsId() {
        return cartsId;
    }

    public void setCartsId(Integer cartsId) {
        this.cartsId = cartsId;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public boolean isEmpty() {
        return isEmpty;
    }

    public void setEmpty(boolean empty) {
        isEmpty = empty;
    }

    @Override
    public String toString() {
        return "Carts{" +
                "cartsId=" + cartsId +
                ", user=" + user +
                ", isEmpty=" + isEmpty +
                '}';
    }
}

