package com.diplom_proj.shop.dto;

import lombok.Data;

@Data
public class AssemblingOrdersDTO {
    private Integer quantityProduct;
    private Integer SumQuantityesProducts;
    private Integer productId;
    private Integer orderId;
    private String City;
}