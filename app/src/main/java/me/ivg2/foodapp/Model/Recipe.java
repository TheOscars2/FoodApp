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
    private String cookTime;
    private Bitmap imageBitmap;
<<<<<<< HEAD
=======
    private String sourceLink;

>>>>>>> Recipes and barcodes pulling from backend.
    private ArrayList<Food> ingredientsMissing;
    private ArrayList<Food> ingredients;
    private ArrayList<String> instructions;

    public Recipe() {
        ingredientsMissing = new ArrayList<>();
        ingredients = new ArrayList<>();
        instructions = new ArrayList<>();
    }

    public Recipe(String name, String imageUrl, String source, int hr, int min) {
        this.name = name;
        this.imageUrl = imageUrl;
        ingredients = new ArrayList<>();
        instructions = new ArrayList<>();
        cookTimeHours = hr;
        cookTimeMinutes = min;
        this.source = source;
        ingredientsMissing = new ArrayList<>();
    }

    public Recipe(String name, String imageUrl, String source, ArrayList<Food> ingredients, ArrayList<String> instructions) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.source = source;
        this.ingredients = ingredients;
        this.instructions = instructions;
        cookTimeHours = 0;
        cookTimeMinutes = 0;
        ingredientsMissing = new ArrayList<>();
    }

    private String formatTime(int minutes, int hours) {
        String formattedCookTime = "";
        if (hours > 0 && hours != 0) {
            formattedCookTime += hours + "h ";
        }
        if (minutes > 0) {
            formattedCookTime += minutes + "m ";
        }
        return formattedCookTime;
    }

    public Recipe(String name, String source, int cookTimeHours, int cookTimeMinutes) {
        this.name = name;
        this.source = source;
        this.cookTimeHours = cookTimeHours;
        this.cookTimeMinutes = cookTimeMinutes;
        this.cookTime = formatTime(cookTimeMinutes, cookTimeHours);
        ingredients = new ArrayList<>();
        instructions = new ArrayList<>();
        ingredientsMissing = new ArrayList<>();
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

    public int getCookTimeMinutes() {
        return cookTimeMinutes;
    }

    public String getCookTime() {
        return formatTime(getCookTimeMinutes(), getCookTimeHours());
    }

    public void setCookTimeMinutes(int cookTimeMinutes) {
        this.cookTimeMinutes = cookTimeMinutes;
    }

    public ArrayList<Food> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<Food> ingredients) {
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

    public ArrayList<Food> getIngredientsMissing() {
        return ingredientsMissing;
    }

    public void setIngredientsMissing(ArrayList<Food> ingredientsMissing) {
        this.ingredientsMissing = ingredientsMissing;
    }

    public void setCookTimeHours(int cookTimeHours) {
        this.cookTimeHours = cookTimeHours;
    }

    public void setCookTime(String cookTime) {
        this.cookTime = cookTime;
    }

    public String getSourceLink() {
        return sourceLink;
    }

    public void setSourceLink(String sourceLink) {
        this.sourceLink = sourceLink;
    }
}
