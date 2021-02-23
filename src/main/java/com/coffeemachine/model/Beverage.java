package com.coffeemachine.model;

public class Beverage {

    private Integer id;
    private String name;
    private Integer quantity;

   public Beverage() { }

    public Beverage(String name, Integer quantity) {
      this(null, name, quantity);
   }

    public Beverage(Integer id, String name, Integer quantity) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }


    @Override
    public String toString() {
        return "Beverage["
                + "id=" + id
                + ", name=" + name
                + ", quantity=" + quantity
                + ']';
    }
}
