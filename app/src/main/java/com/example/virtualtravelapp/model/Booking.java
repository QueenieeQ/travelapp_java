package com.example.virtualtravelapp.model;

public class Booking {
    private String name;

    private String image;
    private int price;
    private int quantity;

    public Booking(String name, int price, int quantity) {
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }



    public Booking(String name, String image, int price, int quantity) {
        this.name = name;
        this.image = image;
        this.price = price;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public Booking() {
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

}
