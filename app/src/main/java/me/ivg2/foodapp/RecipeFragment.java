package me.ivg2.foodapp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import me.ivg2.foodapp.Model.Food;
import me.ivg2.foodapp.Model.Recipe;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecipeFragment extends Fragment {
    @BindView(R.id.floatingAddRecipe)
    FloatingActionButton floatingAddButton;
    @BindView(R.id.rvRecipe)
    RecyclerView rvRecipes;
    RecipeItemRepository recipes;
    private RecipeAdapter recipeAdapter;
    private Unbinder unbinder;

    interface Callback {
        void goToRecipeDetail(Recipe recipe, int index);

        void goToAddRecipe();

        void goToEditRecipe(int index);
    }

    private static Callback callback;

    public RecipeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recipe, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        unbinder = ButterKnife.bind(this, view);
        floatingAddButton = view.findViewById(R.id.floatingAddRecipe);

        floatingAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callback.goToAddRecipe();
            }
        });

        loadRecommendedRecipes();

        rvRecipes = (RecyclerView) view.findViewById(R.id.rvRecipe);
        recipes = RecipeItemRepository.getInstance();
        recipeAdapter = new RecipeAdapter(recipes);
        rvRecipes.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvRecipes.setAdapter(recipeAdapter);
    }

    public void loadRecommendedRecipes() {
        ArrayList<Recipe> allRecipes = setUpRecipeList();
        ArrayList<Food> fridge = FoodItemRepository.getAll();
        RecipeItemRepository.clear();

        ArrayList<ArrayList<Food>> ingredientsMissingFromRecipes = new ArrayList<ArrayList<Food>>();

        //loop over every recipe given
        for (int i = 0; i < allRecipes.size(); i++) {
            boolean isRecipeValid = true;
            ArrayList<Food> recipe = new ArrayList<>();
            //loop over every food in recipe ingredients
            ArrayList<Food> recipeIngredients = allRecipes.get(i).getIngredients();

            for (int j = 0; j < recipeIngredients.size(); j++) {
                boolean isIngredientFound = false;
                //compare each individual food to every food in fridge
                for (int k = 0; k < fridge.size(); k++) {
                    if (recipeIngredients.get(j).getName().equalsIgnoreCase(fridge.get(k).getName())) {
                        //found this ingredient in fridge
                        isIngredientFound = true;
                        break;
                    }
                }
                if(!isIngredientFound) {
                    //creates list of all ingredients missing for each recipe in allRecipes
                    isRecipeValid = false;
                    recipe.add(recipeIngredients.get(j));
                }
            }
            ingredientsMissingFromRecipes.add(i, recipe);
            if (isRecipeValid) {
                RecipeItemRepository.create(allRecipes.get(i));
            }
        }

        for (int i = 0; i < ingredientsMissingFromRecipes.size(); i++) {
            if (ingredientsMissingFromRecipes.get(i).size() == 1) {
                RecipeItemRepository.addToEnd(allRecipes.get(i));
                RecipeItemRepository.get(RecipeItemRepository.size() - 1).setIngredientsMissing(ingredientsMissingFromRecipes.get(i));
            }
        }
    }

    /**
     * Currently setting this method up with fake data
     * Eventually pull from API or backend
     */
    public ArrayList<Recipe> setUpRecipeList() {
        ArrayList<Recipe> recipes = new ArrayList<Recipe>();

        ArrayList<Food> ingredients = new ArrayList<>();
        ArrayList<String> instructions = new ArrayList<>();
        ingredients.add(new Food("apple"));
        ingredients.add(new Food("pringles"));
        instructions.add("Step 1");
        recipes.add(new Recipe("recipe 1", "https://purewows3.imgix.net/images/articles/2017_09/Sliced-red-apple-on-wooden-board.jpg?auto=format,compress&cs=strip", "Mother's Recipe", ingredients, instructions));

        ArrayList<Food> ingredients2 = new ArrayList<>();
        ArrayList<String> instructions2 = new ArrayList<>();
        ingredients2.add(new Food("apple"));
        ingredients2.add(new Food("pringles"));
        ingredients2.add(new Food("peanut butter"));
        instructions2.add("Step 1");
        recipes.add(new Recipe("recipe 2", "https://img.sndimg.com/food/image/upload/w_896,h_504,c_fill,fl_progressive,q_80/v1/img/recipes/21/83/90/picZLz4xF.jpg", "Mother's Recipe", ingredients2, instructions2));

        ArrayList<Food> ingredients3 = new ArrayList<>();
        ArrayList<String> instructions3 = new ArrayList<>();
        ingredients3.add(new Food("apple"));
        ingredients3.add(new Food("pear"));
        ingredients3.add(new Food("banana"));
        ingredients3.add(new Food("smoothie"));
        instructions3.add("Step 1");
        recipes.add(new Recipe("recipe 3", "https://hips.hearstapps.com/hmg-prod.s3.amazonaws.com/images/pasta-salad-horizontal-jpg-1522265695.jpg", "Mother's Recipe", ingredients3, instructions3));

        ArrayList<Food> ingredients4 = new ArrayList<>();
        ArrayList<String> instructions4 = new ArrayList<>();
        ingredients4.add(new Food("spaghetti"));
        ingredients4.add(new Food("egg"));
        ingredients4.add(new Food("chives"));
        instructions4.add("Step 1");
        recipes.add(new Recipe("Spaghetii Carbonara", "https://imagesvc.timeincapp.com/v3/mm/image?url=http%3A%2F%2Fcdn-image.myrecipes.com%2Fsites%2Fdefault%2Ffiles%2Fstyles%2Fmedium_2x%2Fpublic%2Fimage%2Frecipes%2Fck%2F11%2F04%2Ffettuccine-olive-oil-ck-x.jpg%3Fitok%3DN9u99OOY&w=700&q=85", "Food.com", ingredients4, instructions4));

        ArrayList<Food> ingredients5 = new ArrayList<>();
        ArrayList<String> instructions5 = new ArrayList<>();
        ingredients5.add(new Food("ravioli"));
        ingredients5.add(new Food("tomato sauce"));
        instructions5.add("Step 1");
        recipes.add(new Recipe("Ravioli", "https://imagesvc.timeincapp.com/v3/mm/image?url=https%3A%2F%2Fimg1.southernliving.timeinc.net%2Fsites%2Fdefault%2Ffiles%2Fstyles%2Fmedium_2x%2Fpublic%2Fimage%2F2015%2F11%2Fmain%2Ffofoma051120100_0.jpg%3Fitok%3DuG-PCDzS&w=700&q=85", "Recipes.com", ingredients5, instructions5));

        return recipes;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Callback) {
            callback = (Callback) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        callback = null;
    }

    public static void onRecipeClicked(Recipe recipe, int index) {
        callback.goToRecipeDetail(recipe, index);
    }

    public static void onEditClicked(int index) {
        callback.goToEditRecipe(index);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.floatingAddRecipe)
    public void tryToAddRecipe() {
        callback.goToAddRecipe();
    }
}
