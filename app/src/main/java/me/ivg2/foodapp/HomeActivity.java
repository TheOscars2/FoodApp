package me.ivg2.foodapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import me.ivg2.foodapp.Model.Food;

public class HomeActivity extends AppCompatActivity implements RecipeFragment.Callback, AddFoodFragment.Callback, FridgeFragment.Callback{

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
    public void goToRecipeDetail() {
        fragmentManager.beginTransaction().replace(R.id.homeFragment, new RecipeDetailFragment()).commit();
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
    public void goToFoodDetail(Food food) {
        Bundle arguments = new Bundle();
        arguments.putString("image_url", food.getImageURL());
        arguments.putString("expiration_date", food.getExpirationDate().toString());
        arguments.putString("quantity", Double.toString(food.getQuantity()));
        arguments.putString("name", food.getName());

        FoodDetailFragment foodDetailFragment = new FoodDetailFragment();
        foodDetailFragment.setArguments(arguments);

        fragmentManager.beginTransaction().replace(R.id.homeFragment, foodDetailFragment).commit();
    }
}
