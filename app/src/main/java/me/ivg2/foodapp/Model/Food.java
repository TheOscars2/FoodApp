package me.ivg2.foodapp.Model;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import java.util.Comparator;

import static java.lang.String.format;

public class Food {
    private String name;
    private int quantity;
    private DateTime expirationDate;
    private String imageURL;
    private String unit;

    public Food(String name) {
        this.name = name;
        imageURL = null;
        String eventDate = "10/28/2018";
        String eventTime = "00:00";
        expirationDate = DateTime.parse(format("%s %s", eventDate, eventTime), DateTimeFormat.forPattern("MM/dd/yyyy HH:mm"));
    }

    public Food(String name, int quantity, DateTime expirationDate) {
        this.name = name;
        this.quantity = quantity;
        imageURL = null;
        String eventDate = expirationDate.getMonthOfYear() + "/" + expirationDate.getDayOfMonth() + "/" + expirationDate.getYear();
        String eventTime = "00:00";
        this.expirationDate = DateTime.parse(format("%s %s", eventDate, eventTime), DateTimeFormat.forPattern("MM/dd/yyyy HH:mm"));
    }

    public Food(String name, String imageURL) {
        this.name = name;
        this.imageURL = imageURL;
        String eventDate = "10/28/2018";
        String eventTime = "00:00";
        expirationDate = DateTime.parse(format("%s %s", eventDate, eventTime), DateTimeFormat.forPattern("MM/dd/yyyy HH:mm"));
    }

    public Food(String name, int quantity, DateTime expirationDate, String imageURL) {
        this.name = name;
        this.quantity = quantity;
        this.imageURL = imageURL;
        String eventDate = expirationDate.getMonthOfYear() + "/" + expirationDate.getDayOfMonth() + "/" + expirationDate.getYear();
        String eventTime = "00:00";
        this.expirationDate = DateTime.parse(format("%s %s", eventDate, eventTime), DateTimeFormat.forPattern("MM/dd/yyyy HH:mm"));
    }

    public Food(String name, int quantity, String unit) {
        this.name = name;
        this.quantity = quantity;
        this.unit = unit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public DateTime getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(DateTime expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

<<<<<<< HEAD
    public static final Comparator<Food> ALPHABETICAL = new Comparator<Food>() {
        @Override
        public int compare(Food o1, Food o2) {
            return o1.getName().compareTo(o2.getName());
        }
    };
    
=======
>>>>>>> 6d8d619... Added recycler view and fragments for grocery list.
    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
