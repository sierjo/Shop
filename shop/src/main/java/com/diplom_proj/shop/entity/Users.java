package com.diplom_proj.shop.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer userId;
    @Column(name = "email")
    private String email;
    @Column(name = "password")
    private String password;
    @Column(name = "phone_number")
    private String phoneNumber;
    @Column(name = "is_active")
    private boolean isActive;
    @Column(name = "registration_date", updatable = false)
    @CreationTimestamp  // Аннотация говорит, что Hibernate сам поставит текущее время
    @JsonFormat(pattern = "dd-MM-yy")
    private LocalDateTime registratiomDate;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id", referencedColumnName = "user_type_id")
    private Roles roleId;

    @OneToMany(targetEntity = Address.class, mappedBy = "users", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Address> addresses = new ArrayList<>();
    @OneToMany(targetEntity = Orders.class, mappedBy = "users", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Orders> orders = new ArrayList<>();
    @OneToMany(targetEntity = FavoriteProducts.class, mappedBy = "users", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FavoriteProducts> favoriteProducts = new ArrayList<>();

    public Users() {
    }

    public Users(Integer userId, String email, String password, String phoneNumber, boolean isActive, LocalDateTime registratiomDate, Roles roleId, List<Address> addresses, List<Orders> orders, List<FavoriteProducts> favoriteProducts) {
        this.userId = userId;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.isActive = isActive;
        this.registratiomDate = registratiomDate;
        this.roleId = roleId;
        this.addresses = addresses;
        this.orders = orders;
        this.favoriteProducts = favoriteProducts;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public LocalDateTime getRegistratiomDate() {
        return registratiomDate;
    }

    public void setRegistratiomDate(LocalDateTime registratiomDate) {
        this.registratiomDate = registratiomDate;
    }

    public Roles getRoleId() {
        return roleId;
    }

    public void setRoleId(Roles roleId) {
        this.roleId = roleId;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

    public List<Orders> getOrders() {
        return orders;
    }

    public void setOrders(List<Orders> orders) {
        this.orders = orders;
    }

    public List<FavoriteProducts> getFavoriteProducts() {
        return favoriteProducts;
    }

    public void setFavoriteProducts(List<FavoriteProducts> favoriteProducts) {
        this.favoriteProducts = favoriteProducts;
    }

    @Override
    public String toString() {
        return "Users{" +
                "userId=" + userId +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", isActive=" + isActive +
                ", registratiomDate=" + registratiomDate +
                ", roleId=" + roleId +
                ", addresses=" + addresses +
                ", orders=" + orders +
                ", favoriteProducts=" + favoriteProducts +
                '}';
    }
}
