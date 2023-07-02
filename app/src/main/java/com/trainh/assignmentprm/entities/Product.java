package com.trainh.assignmentprm.entities;

import java.io.Serializable;

public class Product implements Serializable {
    private int idCart;
    private int id;
    private int image;
    private String name;
    private double price;
    private int quantity;
    private String type;
    private String description;

    public Product(int image, String name, double price, int quantity, String type, String description) {
        this.image = image;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.type = type;
        this.description = description;
    }

    public Product(int idCart, int id, int image, String name, double price, int quantity) {
        this.idCart = idCart;
        this.id = id;
        this.image = image;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public Product(int id, int image, String name, double price, int quantity, String type, String description) {
        this.id = id;
        this.image = image;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.type = type;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public int getIdCart() {
        return idCart;
    }

    public void setIdCart(int idCart) {
        this.idCart = idCart;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
