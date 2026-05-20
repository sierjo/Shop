package com.diplom_proj.shop.dto;

public class DeliveryPositionOrdersDTO {
    private String productName;
    private int quantity;
    private int itemPrice;

    public DeliveryPositionOrdersDTO(String productName, int quantity, int itemPrice) {
        this.productName = productName;
        this.quantity = quantity;
        this.itemPrice = itemPrice;
    }

    public int getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(int itemPrice) {
        this.itemPrice = itemPrice;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
