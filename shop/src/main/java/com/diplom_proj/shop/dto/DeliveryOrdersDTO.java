package com.diplom_proj.shop.dto;

public class DeliveryOrdersDTO {
    private int orderId;
    private String fullName;
    private String city;
    private String additionDate;
    private int sumCostOrder;

    public DeliveryOrdersDTO(int orderId, String fullName, String city,
                             String additionDate, int sumCostOrder) {
        this.orderId = orderId;
        this.fullName = fullName;
        this.city = city;
        this.additionDate = additionDate;
        this.sumCostOrder = sumCostOrder;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAdditionDate() {
        return additionDate;
    }

    public void setAdditionDate(String additionDate) {
        this.additionDate = additionDate;
    }

    public int getSumCostOrder() {
        return sumCostOrder;
    }

    public void setSumCostOrder(int sumCostOrder) {
        this.sumCostOrder = sumCostOrder;
    }
}
