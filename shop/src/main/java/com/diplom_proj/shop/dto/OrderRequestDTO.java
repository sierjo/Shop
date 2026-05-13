package com.diplom_proj.shop.dto;

import lombok.Data;

@Data
public class OrderRequestDTO {
    private String firstName;
    private String lastName;
    private String phoneCode;
    private String phoneNumber;
    private String country;
    private String street;
    private String buildingNo;
    private String aptNo;
    private String zipCode;
    private String city;
}
