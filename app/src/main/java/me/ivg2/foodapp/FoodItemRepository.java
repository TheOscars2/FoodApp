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
        foods.add(new Food("bananas", "https://images-na.ssl-images-amazon.com/images/I/71gI-IUNUkL._SY355_.jpg"));
        foods.add(new Food("all purpose flour"));
        foods.add(new Food("apples"));
        foods.add(new Food("artichoke hearts"));
        foods.add(new Food("bacon"));
        foods.add(new Food("baking powder"));
        foods.add(new Food("baking soda"));
        foods.add(new Food("basmati rice"));
        foods.add(new Food("black pepper"));
        foods.add(new Food("black peppercorns"));
        foods.add(new Food("butter"));
        foods.add(new Food("buttermilk"));
        foods.add(new Food("cake flour"));
        foods.add(new Food("canned cannellini beans"));
        foods.add(new Food("canned garbanzo beans"));
        foods.add(new Food("caramels"));
        foods.add(new Food("cashews"));
        foods.add(new Food("cayenne pepper"));
        foods.add(new Food("chicken breasts"));
        foods.add(new Food("chicken broth"));
        foods.add(new Food("chocolate chips"));
        foods.add(new Food("chuck steak"));
        foods.add(new Food("cinnamon"));
        foods.add(new Food("coarse salt"));
        foods.add(new Food("cocoa powder"));
        foods.add(new Food("corn syrup"));
        foods.add(new Food("cream"));
        foods.add(new Food("cream cheese"));
        foods.add(new Food("cucumber"));
        foods.add(new Food("dried thyme"));
        foods.add(new Food("egg"));
        foods.add(new Food("egg whites"));
        foods.add(new Food("eggs"));
        foods.add(new Food("fat free yogurt"));
        foods.add(new Food("flax seed"));
        foods.add(new Food("flour"));
        foods.add(new Food("flour tortillas"));
        foods.add(new Food("fresh basil leaves"));
        foods.add(new Food("garlic"));
        foods.add(new Food("garlic clove"));
        foods.add(new Food("granulated sugar"));
        foods.add(new Food("ground ginger"));
        foods.add(new Food("hamburger buns"));
        foods.add(new Food("instant yeast"));
        foods.add(new Food("kabocha squash"));
        foods.add(new Food("kosher salt"));
        foods.add(new Food("lemon juice"));
        foods.add(new Food("lemon zest"));
        foods.add(new Food("lettuce leaves"));
        foods.add(new Food("low fat mayo"));
        foods.add(new Food("maple syrup"));
        foods.add(new Food("mayonnaise"));
        foods.add(new Food("milk"));
        foods.add(new Food("nutmeg"));
        foods.add(new Food("olive oil"));
        foods.add(new Food("onion"));
        foods.add(new Food("onions"));
        foods.add(new Food("oregano"));
        foods.add(new Food("parmesan cheese"));
        foods.add(new Food("pecorino romano"));
        foods.add(new Food("pepperoni"));
        foods.add(new Food("powdered sugar"));
        foods.add(new Food("prosciutto"));
        foods.add(new Food("provolone cheese"));
        foods.add(new Food("red food coloring"));
        foods.add(new Food("red onion"));
        foods.add(new Food("red wine vinegar"));
        foods.add(new Food("romaine hearts"));
        foods.add(new Food("roquefort cheese"));
        foods.add(new Food("rustic bread"));
        foods.add(new Food("salt"));
        foods.add(new Food("salt and pepper"));
        foods.add(new Food("semi sweet chocolate chips"));
        foods.add(new Food("sguar"));
        foods.add(new Food("sirloin steak"));
        foods.add(new Food("sugar"));
        foods.add(new Food("tomato"));
        foods.add(new Food("tomatoes"));
        foods.add(new Food("unsalted butter"));
        foods.add(new Food("vanilla"));
        foods.add(new Food("vanilla extract"));
        foods.add(new Food("vegetable oil"));
        foods.add(new Food("vinegar"));
        foods.add(new Food("walnuts"));
        foods.add(new Food("water"));
        foods.add(new Food("white wine vinegar"));
        foods.add(new Food("whole wheat flour"));
        foods.add(new Food("fresh parsley"));
        foods.add(new Food("gorgonzola"));
        foods.add(new Food("olive oil"));
        foods.add(new Food("onions"));
        foods.add(new Food("pine nuts"));
        foods.add(new Food("quinoa"));
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

    public ArrayList<String> getFoodNames() {
        ArrayList<String> allFoodNames = new ArrayList<>();
        for (Food f : getInstance().foods) {
            allFoodNames.add(f.getName());
        }
        return allFoodNames;
    }

    public static int size() {
        return getInstance().foods.size();
    }

    public static int getIndex(Food f) {
        return getAll().indexOf(f);
    }
}
