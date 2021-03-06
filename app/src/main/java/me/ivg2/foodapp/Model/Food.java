package me.ivg2.foodapp.Model;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import java.util.Comparator;
import java.util.Random;

import static java.lang.String.format;

public class Food {
    private String name;
    private double quantity;
    private DateTime expirationDate;
    private String imageURL;
    private String unit;

    public Food() {

    }

    public Food(String name) {
        this.name = name;
        imageURL = null;
        this.unit = "servings";
        this.quantity = 2;

        //randomly assign exp dates
        Random gen = new Random();
        int range = 300;
        DateTime randomDate = DateTime.now().plusDays(gen.nextInt(range));
        expirationDate = randomDate;

        //add in some expired foods
        gen = new Random();
        range = 30;
        if(gen.nextInt(range) == 1) {
            expirationDate = DateTime.now().minusDays(2);
        }
    }

    public Food(String name, double quantity, DateTime expirationDate) {
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
        this.quantity = 1;
        this.unit = "servings";
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

    public Food(Food f) {
        this.name = f.getName();
        this.quantity = f.getQuantity();
        this.unit = f.getUnit();
        this.expirationDate = f.getExpirationDate();
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

    public static final Comparator<Food> ALPHABETICAL = new Comparator<Food>() {
        @Override
        public int compare(Food o1, Food o2) {
            return o1.getName().toLowerCase().compareTo(o2.getName().toLowerCase());
        }
    };
    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
