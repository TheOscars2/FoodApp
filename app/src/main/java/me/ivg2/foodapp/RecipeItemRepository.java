package me.ivg2.foodapp;

import java.util.ArrayList;

import me.ivg2.foodapp.Model.Recipe;

class RecipeItemRepository   {

    private ArrayList<Recipe> recipes;

    private void load() {
        recipes = new ArrayList<>();

        //input manual data for recipes for reference
        recipes.add(new Recipe("Spaghetii Carbonara", "https://imagesvc.timeincapp.com/v3/mm/image?url=http%3A%2F%2Fcdn-image.myrecipes.com%2Fsites%2Fdefault%2Ffiles%2Fstyles%2Fmedium_2x%2Fpublic%2Fimage%2Frecipes%2Fck%2F11%2F04%2Ffettuccine-olive-oil-ck-x.jpg%3Fitok%3DN9u99OOY&w=700&q=85", "Food.com", 1, 12));
        recipes.add(new Recipe("Ravioli", "https://imagesvc.timeincapp.com/v3/mm/image?url=https%3A%2F%2Fimg1.southernliving.timeinc.net%2Fsites%2Fdefault%2Ffiles%2Fstyles%2Fmedium_2x%2Fpublic%2Fimage%2F2015%2F11%2Fmain%2Ffofoma051120100_0.jpg%3Fitok%3DuG-PCDzS&w=700&q=85", "Recipes.com", 0, 45));
        recipes.add(new Recipe("Spaghetii Carbonara", "https://imagesvc.timeincapp.com/v3/mm/image?url=http%3A%2F%2Fcdn-image.myrecipes.com%2Fsites%2Fdefault%2Ffiles%2Fstyles%2Fmedium_2x%2Fpublic%2Fimage%2Frecipes%2Fck%2F11%2F04%2Ffettuccine-olive-oil-ck-x.jpg%3Fitok%3DN9u99OOY&w=700&q=85", "Food.com", 1, 12));
        recipes.add(new Recipe("Ravioli", "https://imagesvc.timeincapp.com/v3/mm/image?url=https%3A%2F%2Fimg1.southernliving.timeinc.net%2Fsites%2Fdefault%2Ffiles%2Fstyles%2Fmedium_2x%2Fpublic%2Fimage%2F2015%2F11%2Fmain%2Ffofoma051120100_0.jpg%3Fitok%3DuG-PCDzS&w=700&q=85", "Recipes.com", 0, 45));
        recipes.add(new Recipe("Spaghetii Carbonara", "https://imagesvc.timeincapp.com/v3/mm/image?url=http%3A%2F%2Fcdn-image.myrecipes.com%2Fsites%2Fdefault%2Ffiles%2Fstyles%2Fmedium_2x%2Fpublic%2Fimage%2Frecipes%2Fck%2F11%2F04%2Ffettuccine-olive-oil-ck-x.jpg%3Fitok%3DN9u99OOY&w=700&q=85", "Food.com", 1, 12));
        recipes.add(new Recipe("Ravioli", "https://imagesvc.timeincapp.com/v3/mm/image?url=https%3A%2F%2Fimg1.southernliving.timeinc.net%2Fsites%2Fdefault%2Ffiles%2Fstyles%2Fmedium_2x%2Fpublic%2Fimage%2F2015%2F11%2Fmain%2Ffofoma051120100_0.jpg%3Fitok%3DuG-PCDzS&w=700&q=85", "Recipes.com", 0, 45));
        recipes.add(new Recipe("Spaghetii Carbonara", "https://imagesvc.timeincapp.com/v3/mm/image?url=http%3A%2F%2Fcdn-image.myrecipes.com%2Fsites%2Fdefault%2Ffiles%2Fstyles%2Fmedium_2x%2Fpublic%2Fimage%2Frecipes%2Fck%2F11%2F04%2Ffettuccine-olive-oil-ck-x.jpg%3Fitok%3DN9u99OOY&w=700&q=85", "Food.com", 1, 12));
        recipes.add(new Recipe("Ravioli", "https://imagesvc.timeincapp.com/v3/mm/image?url=https%3A%2F%2Fimg1.southernliving.timeinc.net%2Fsites%2Fdefault%2Ffiles%2Fstyles%2Fmedium_2x%2Fpublic%2Fimage%2F2015%2F11%2Fmain%2Ffofoma051120100_0.jpg%3Fitok%3DuG-PCDzS&w=700&q=85", "Recipes.com", 0, 45));

        ArrayList<String> instructions = new ArrayList<>();
        ArrayList<String> ingredients = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            instructions.add("instruction number " + (i + 1));
            ingredients.add("ingredient number " + (i + 1));
        }

        recipes.get(0).setInstructions(instructions);
        recipes.get(0).setIngredients(ingredients);
        recipes.get(1).setInstructions(instructions);
        recipes.get(1).setIngredients(ingredients);
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

    public static int size() {
        return getInstance().recipes.size();
    }
}
