package com.coffeemachine.model;

public class Sale {

    private Integer id;
    private String customer;
    private String beverage;

    public Sale() {
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getBeverage() {
        return beverage;
    }

    public void setBeverage(String beverage) {
        this.beverage = beverage;
    }


    @Override
    public String toString() {
        return "Sale["
                + "id=" + id
                + ", customer=" + customer
                + ", beverage=" + beverage
                + ']';


    }
}
