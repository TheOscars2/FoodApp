package me.ivg2.foodapp.Model;

import java.util.Date;

public class Food {
    private String name;
    private double quantity;
    private Date expirationDate;
    private String imageURL;

    public Food(String name) {
        this.name = name;
        imageURL = null;
    }

    public Food(String name, double quantity, Date expirationDate) {
        this.name = name;
        this.quantity = quantity;
        this.expirationDate = expirationDate;
        imageURL = null;
    }

    public Food(String name, String imageURL) {
        this.name = name;
        this.imageURL = imageURL;
        expirationDate = new Date();
    }

    public Food(String name, double quantity, Date expirationDate, String imageURL) {
        this.name = name;
        this.quantity = quantity;
        this.expirationDate = expirationDate;
        this.imageURL = imageURL;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}
