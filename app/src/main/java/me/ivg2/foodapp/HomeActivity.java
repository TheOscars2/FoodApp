package me.ivg2.foodapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import me.ivg2.foodapp.Model.Recipe;
import me.ivg2.foodapp.barcode.BarcodeFragment;


public class HomeActivity extends AppCompatActivity implements RecipeFragment.Callback, AddFoodFragment.Callback, FridgeFragment.Callback, AddRecipeFragment.Callback, ManualAddFragment.Callback, BarcodeFragment.Callback, PluFragment.Callback, RecipeDetailFragment.Callback, FoodDetailFragment.Callback {


    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        fragmentManager = getSupportFragmentManager();

        final Fragment recipeFragment = new RecipeFragment();
        final Fragment addFoodFragment = new AddFoodFragment();
        final Fragment fridgeFragment = new FridgeFragment();

        fragmentManager.beginTransaction().replace(R.id.homeFragment, recipeFragment).commit();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.list:
                        fragmentManager.beginTransaction().replace(R.id.homeFragment, recipeFragment).commit();
                        return true;
                    case R.id.apple:
                        fragmentManager.beginTransaction().replace(R.id.homeFragment, addFoodFragment).commit();
                        return true;
                    case R.id.fridge:
                        fragmentManager.beginTransaction().replace(R.id.homeFragment, fridgeFragment).commit();
                        return true;
                }
                return false;
            }
        });
    }

    @Override
    public void goToRecipeDetail(Recipe recipe, int index) {
        Bundle bundle = new Bundle();
        bundle.putString("image_url", recipe.getImageUrl());
        bundle.putString("name", recipe.getName());
        bundle.putString("source", recipe.getSource());
        bundle.putInt("hours", recipe.getCookTimeHours());
        bundle.putInt("min", recipe.getCookTimeMinutes());
        bundle.putStringArrayList("ingredients", recipe.getIngredients());
        bundle.putStringArrayList("instructions", recipe.getInstructions());
        bundle.putInt("index", index);

        RecipeDetailFragment recipeDetailFragment = new RecipeDetailFragment();
        recipeDetailFragment.setArguments(bundle);

        fragmentManager.beginTransaction().replace(R.id.homeFragment, recipeDetailFragment).commit();
    }

    @Override
    public void goToAddRecipe() {
        fragmentManager.beginTransaction().replace(R.id.homeFragment, new AddRecipeFragment()).commit();
    }

    @Override
    public void goToBarcodeScanner() {
        fragmentManager.beginTransaction().replace(R.id.homeFragment, new BarcodeFragment()).commit();
    }
    @Override
    public void goToPLUInput() {
        fragmentManager.beginTransaction().replace(R.id.homeFragment, new PluFragment()).commit();
    }

    @Override
    public void goToManualFoodAddition() {
        fragmentManager.beginTransaction().replace(R.id.homeFragment, new ManualAddFragment()).commit();
    }

    @Override
    public void goToManualFoodAdditionfromBarcode(String foodName) {
        Bundle arguments = new Bundle();

        arguments.putString("productName", foodName);

        ManualAddFragment manualAddFragment = new ManualAddFragment();
        manualAddFragment.setArguments(arguments);

        fragmentManager.beginTransaction().replace(R.id.homeFragment, manualAddFragment).commit();
    }

    @Override
    public void goToFoodDetail(int index) {
        Bundle arguments = new Bundle();
        arguments.putInt("index", index);

        FoodDetailFragment foodDetailFragment = new FoodDetailFragment();
        foodDetailFragment.setArguments(arguments);

        fragmentManager.beginTransaction().replace(R.id.homeFragment, foodDetailFragment).commit();
    }

    @Override
    public void goToAddIngredients() {
        Intent intent = new Intent(this, ManualAddIngredientsActivity.class);
        startActivity(intent);
    }

    @Override
    public void goToFridge() {
        fragmentManager.beginTransaction().replace(R.id.homeFragment, new FridgeFragment()).commit();
    }

    @Override
    public void goToEditRecipe(int index) {
        Bundle arguments = new Bundle();
        arguments.putInt("index", index);
        AddRecipeFragment arFrag = new AddRecipeFragment();
        arFrag.setArguments(arguments);

        fragmentManager.beginTransaction().replace(R.id.homeFragment, arFrag).commit();
    }


    @Override
    public void goToEditIngredients(int index) {
        Intent intent = new Intent(this, ManualAddIngredientsActivity.class);
        intent.putExtra("index", index);
        startActivity(intent);
    }

    @Override
    public void goToManualFromPlu(String foodName) {
        Bundle arguments = new Bundle();
        arguments.putString("productName", foodName);
        ManualAddFragment manualAddFragment = new ManualAddFragment();
        manualAddFragment.setArguments(arguments);
        fragmentManager.beginTransaction().replace(R.id.homeFragment, manualAddFragment).commit();
    }

    @Override
    public void goToRecipes() {
        fragmentManager.beginTransaction().replace(R.id.homeFragment, new RecipeFragment()).commit();
    }

    @Override
    public void goToEditFood(int index) {
        Bundle arguments = new Bundle();
        arguments.putInt("index", index);
        ManualAddFragment manualAddFragment = new ManualAddFragment();
        manualAddFragment.setArguments(arguments);

        fragmentManager.beginTransaction().replace(R.id.homeFragment, manualAddFragment).commit();
    }
}
