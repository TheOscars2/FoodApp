package me.ivg2.foodapp;

import java.util.ArrayList;

import me.ivg2.foodapp.Model.Food;

public class FoodItemRepository {
    private ArrayList<Food> foods;

    //will load from a server but for now I will hardcode Food data in
    private void load() {
        foods = new ArrayList<>();
        foods.add(new Food("apple", "https://www.vaporfi.com.au/media/catalog/product/cache/34/thumbnail/600x600/9df78eab33525d08d6e5fb8d27136e95/v/z/vz_eliquid_juicy_red_apple.jpg"));
        foods.add(new Food("pear", "http://vaporhauschicago.com/wp-content/uploads/2017/06/pear.jpg"));
        foods.add(new Food("pizza", "https://www.messforless.net/wp-content/uploads/2018/01/2-ingredient-pizza-dough-weight-watchers-9.jpg"));
        foods.add(new Food("smoothie", "http://images.media-allrecipes.com/userphotos/960x960/3756353.jpg"));
        foods.add(new Food("pringles", "https://images-na.ssl-images-amazon.com/images/I/71LeHo4XhwL._SY679_.jpg"));
        foods.add(new Food("banana", "https://images-na.ssl-images-amazon.com/images/I/71gI-IUNUkL._SY355_.jpg"));
        foods.add(new Food("apple", "https://www.vaporfi.com.au/media/catalog/product/cache/34/thumbnail/600x600/9df78eab33525d08d6e5fb8d27136e95/v/z/vz_eliquid_juicy_red_apple.jpg"));
        foods.add(new Food("pear", "http://vaporhauschicago.com/wp-content/uploads/2017/06/pear.jpg"));
        foods.add(new Food("pizza", "https://www.messforless.net/wp-content/uploads/2018/01/2-ingredient-pizza-dough-weight-watchers-9.jpg"));
        foods.add(new Food("smoothie", "http://images.media-allrecipes.com/userphotos/960x960/3756353.jpg"));
        foods.add(new Food("pringles", "https://images-na.ssl-images-amazon.com/images/I/71LeHo4XhwL._SY679_.jpg"));
        foods.add(new Food("banana", "https://images-na.ssl-images-amazon.com/images/I/71gI-IUNUkL._SY355_.jpg"));
        foods.add(new Food("apple", "https://www.vaporfi.com.au/media/catalog/product/cache/34/thumbnail/600x600/9df78eab33525d08d6e5fb8d27136e95/v/z/vz_eliquid_juicy_red_apple.jpg"));
        foods.add(new Food("pear", "http://vaporhauschicago.com/wp-content/uploads/2017/06/pear.jpg"));
        foods.add(new Food("pizza", "https://www.messforless.net/wp-content/uploads/2018/01/2-ingredient-pizza-dough-weight-watchers-9.jpg"));
        foods.add(new Food("smoothie", "http://images.media-allrecipes.com/userphotos/960x960/3756353.jpg"));
        foods.add(new Food("pringles", "https://images-na.ssl-images-amazon.com/images/I/71LeHo4XhwL._SY679_.jpg"));
        foods.add(new Food("banana", "https://images-na.ssl-images-amazon.com/images/I/71gI-IUNUkL._SY355_.jpg"));
    }

    private void save() {
        //upload food to server here
    }

    public ArrayList<Food> getData() {
        return new ArrayList<Food>(foods);
    }

    private static final FoodItemRepository ourInstance = new FoodItemRepository();

    public static FoodItemRepository getInstance() {
        return ourInstance;
    }

    private FoodItemRepository() {
        load();
    }

    public static ArrayList<Food> getAll() {
        return getInstance().foods;
    }

    public static Food get(int index) {
        return getInstance().foods.get(index);
    }

    public static void create(Food newFood) {
        getInstance().foods.add(newFood);
        getInstance().save();
    }

    public static void update(int index, Food newFood) {
        getInstance().foods.set(index, newFood);
        getInstance().save();
    }

    public static void delete(int index) {
        getInstance().foods.remove(index);
        getInstance().save();
    }

    public static int size() {
        return getInstance().foods.size();
    }
}
