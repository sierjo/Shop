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
    private Users user;

    @Column(name = "first_name")
    private String name;
    @Column(name = "last_name")
    private String surname;
    @Column(name = "City")
    private String cityAddress;
    @Column(name = "Country")
    private String countryAddress;
    @Column(name = "Street")
    private String streetAddress;
    @Column(name = "House_number")
    private String houseNumberAddress;
    @Column(name = "Apartment_number")
    private String apartmentNumberAddress;
    @Column(name = "Postal_code")
    private String code;
    @OneToMany(targetEntity = Orders.class, mappedBy = "address", cascade = CascadeType.ALL)
    private List<Orders> orders = new ArrayList<>();

    public Address() {
    }

    public Address(Users user, String name, String surname, String cityAddress, String countryAddress, String streetAddress, String houseNumberAddress, String apartmentNumberAddress, String code, List<Orders> orders) {
        this.user = user;
        this.name = name;
        this.surname = surname;
        this.cityAddress = cityAddress;
        this.countryAddress = countryAddress;
        this.streetAddress = streetAddress;
        this.houseNumberAddress = houseNumberAddress;
        this.apartmentNumberAddress = apartmentNumberAddress;
        this.code = code;
        this.orders = orders;
    }

    public Integer getAdressId() {
        return adressId;
    }

    public void setAdressId(Integer adressId) {
        this.adressId = adressId;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getCityAddress() {
        return cityAddress;
    }

    public void setCityAddress(String cityAddress) {
        this.cityAddress = cityAddress;
    }

    public String getCountryAddress() {
        return countryAddress;
    }

    public void setCountryAddress(String countryAddress) {
        this.countryAddress = countryAddress;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getHouseNumberAddress() {
        return houseNumberAddress;
    }

    public void setHouseNumberAddress(String houseNumberAddress) {
        this.houseNumberAddress = houseNumberAddress;
    }

    public String getApartmentNumberAddress() {
        return apartmentNumberAddress;
    }

    public void setApartmentNumberAddress(String apartmentNumberAddress) {
        this.apartmentNumberAddress = apartmentNumberAddress;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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
                ", user=" + user +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", cityAddress='" + cityAddress + '\'' +
                ", countryAddress='" + countryAddress + '\'' +
                ", streetAddress='" + streetAddress + '\'' +
                ", houseNumberAddress='" + houseNumberAddress + '\'' +
                ", apartmentNumberAddress='" + apartmentNumberAddress + '\'' +
                ", code='" + code + '\'' +
                ", orders=" + orders +
                '}';
    }
}
