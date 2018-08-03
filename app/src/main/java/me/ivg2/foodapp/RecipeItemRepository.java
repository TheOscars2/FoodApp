package me.ivg2.foodapp;

import java.util.ArrayList;

import me.ivg2.foodapp.Model.Recipe;

class RecipeItemRepository {
    private ArrayList<Recipe> recipes;

    private void load() {
        recipes = new ArrayList<>();
    }

    private void save() {
        //upload recipes to server here
    }

    private ArrayList<Recipe> getData() {
        return new ArrayList<Recipe>(recipes);
    }

    private static final RecipeItemRepository ourInstance = new RecipeItemRepository();

    private RecipeItemRepository() {
        load();
    }

    static RecipeItemRepository getInstance() {
        return ourInstance;
    }

    public static ArrayList<Recipe> getAll() {
        return getInstance().recipes;
    }

    public static Recipe get(int index) {
        return getInstance().recipes.get(index);
    }

    public static void create(Recipe newRecipe) {
        getInstance().recipes.add(newRecipe);
        getInstance().save();
    }

    public static void update(int index, Recipe newRecipe) {
        getInstance().recipes.set(index, newRecipe);
        getInstance().save();
    }

    public static void delete(int index) {
        getInstance().recipes.remove(index);
        getInstance().save();
    }

    public static void clear() {
        getInstance().recipes.clear();
        getInstance().save();
    }

    public static int size() {
        return getInstance().recipes.size();
    }

    public static void addToEnd(Recipe recipe) {
        getInstance().recipes.add(getInstance().recipes.size(), recipe);
    }
}
