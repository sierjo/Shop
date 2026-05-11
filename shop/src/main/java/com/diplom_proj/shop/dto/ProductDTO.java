package com.diplom_proj.shop.dto;

import lombok.Data;

@Data
public class ProductDTO {
    private Integer sumAllProductPrice;
    private Integer productId;
    private String productName;
    private String productPhoto;
    private String productDescription;
    private Integer productPrice;
    private Integer quantityProduct;
}

