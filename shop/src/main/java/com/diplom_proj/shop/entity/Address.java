package com.diplom_proj.shop.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "addresses")
public class Address {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer adressId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private Users users;

    @OneToMany(targetEntity = Orders.class, mappedBy = "address", cascade = CascadeType.ALL)
    private List<Orders> orders = new ArrayList<>();

    public Address() {
    }

    public Address(Integer adressId, Users users, List<Orders> orders) {
        this.adressId = adressId;
        this.users = users;
        this.orders = orders;
    }

    public Integer getAdressId() {
        return adressId;
    }

    public void setAdressId(Integer adressId) {
        this.adressId = adressId;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public List<Orders> getOrders() {
        return orders;
    }

    public void setOrders(List<Orders> orders) {
        this.orders = orders;
    }

    @Override
    public String toString() {
        return "Address{" +
                "adressId=" + adressId +
                ", users=" + users +
                ", orders=" + orders +
                '}';
    }
}
