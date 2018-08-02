package me.ivg2.foodapp;

import java.util.ArrayList;

import me.ivg2.foodapp.Model.Food;

class GroceryListItemRepository {
    private ArrayList<Food> groceryList;

    private void load() {
        groceryList = new ArrayList<>();

        groceryList.add(new Food("carrots", 3, "gal"));
        groceryList.add(new Food("yellow onion", 3, "cups"));
        groceryList.add(new Food("potatoes", 1, "cups"));
    }

    public void save() {

    }

    private ArrayList<Food> getData() {
        return new ArrayList<Food>(groceryList);
    }

    private static final GroceryListItemRepository ourInstance = new GroceryListItemRepository();

    static GroceryListItemRepository getInstance() {
        return ourInstance;
    }

    private GroceryListItemRepository() {
        load();
    }

    public static ArrayList<Food> getAll() {
        return getInstance().groceryList;
    }

    public static Food get(int index) {
        return getInstance().groceryList.get(index);
    }

    public static void create(Food newFood) {
        getInstance().groceryList.add(newFood);
        getInstance().save();
    }

    public static void update(int index, Food newFood) {
        getInstance().groceryList.set(index, newFood);
        getInstance().save();
    }

    public static void delete(int index) {
        getInstance().groceryList.remove(index);
        getInstance().save();
    }

    public static void clear() {
        getInstance().groceryList.clear();
        getInstance().save();
    }

    public static int getIndex(Food f){
        return getAll().indexOf(f);
    }

    public static int size() {
        return getInstance().groceryList.size();
    }
}
