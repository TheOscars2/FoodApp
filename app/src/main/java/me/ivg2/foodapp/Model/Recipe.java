package me.ivg2.foodapp.Model;

import android.graphics.Bitmap;

import java.util.ArrayList;

public class Recipe {
    private String name;
    private String imageUrl;
    private String source;
    //different units- min v hour
    private int cookTimeHours;
    private int cookTimeMinutes;
    private Bitmap imageBitmap;

    //The format of these two variables is unknown
    //could format into map with key string ("Sugar") linked to a quantity (2 tbls)
    private ArrayList<String> ingredients;

    //could just be a string too for this one
    private ArrayList<String> instructions;

    public Recipe(String name, String imageUrl, String source, int hr, int min) {
        this.name = name;
        this.imageUrl = imageUrl;

        ingredients = new ArrayList<>();
        instructions = new ArrayList<>();

        cookTimeHours = hr;
        cookTimeMinutes = min;
        this.source = source;
    }

    public Recipe(String name, String imageUrl, String source, ArrayList<String> ingredients, ArrayList<String> instructions) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.source = source;
        this.ingredients = ingredients;
        this.instructions = instructions;

        cookTimeHours = 0;
        cookTimeMinutes = 0;
    }

    public Recipe(String name, String source, int cookTimeHours, int cookTimeMinutes) {
        this.name = name;
        this.source = source;
        this.cookTimeHours = cookTimeHours;
        this.cookTimeMinutes = cookTimeMinutes;
        ingredients = new ArrayList<>();
        instructions = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public int getCookTimeHours() {
        return cookTimeHours;
    }

    public void setCookTimeHours(int cookTimeHours) {
        this.cookTimeHours = cookTimeHours;
    }

    public int getCookTimeMinutes() {
        return cookTimeMinutes;
    }

    public void setCookTimeMinutes(int cookTimeMinutes) {
        this.cookTimeMinutes = cookTimeMinutes;
    }

    public ArrayList<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<String> ingredients) {
        this.ingredients = ingredients;
    }

    public ArrayList<String> getInstructions() {
        return instructions;
    }

    public void setInstructions(ArrayList<String> instructions) {
        this.instructions = instructions;
    }

    public Bitmap getImageBitmap() {
        return imageBitmap;
    }

    public void setImageBitmap(Bitmap imageBitmap) {
        this.imageBitmap = imageBitmap;
    }
}