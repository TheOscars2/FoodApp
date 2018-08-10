package me.ivg2.foodapp;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import me.ivg2.foodapp.Model.Food;
import me.ivg2.foodapp.Model.Recipe;
import me.ivg2.foodapp.server.UrlManager;

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
    private TabLayout tabs;
    private static ArrayList<Recipe> browsingRecipes = new ArrayList<>();
    private static ArrayList<Recipe> cookingRecipes = new ArrayList<>();
    private final int RECIPES_TO_COOK_POSITION = 0;
    private final int RECIPES_CLOSE_TO_COOKING = 1;

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
        rvRecipes = (RecyclerView) view.findViewById(R.id.rvRecipe);
        recipes = RecipeItemRepository.getInstance();
        recipeAdapter = new RecipeAdapter(recipes);
        //recipeAdapter = new RecipeAdapter(RecipeItemRepository.getInstance());
        rvRecipes.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvRecipes.setAdapter(recipeAdapter);
        RecipeItemRepository.set(cookingRecipes);
        refreshRecommendedRecipes();
        rvRecipes.scrollToPosition(0);
        recipeAdapter.notifyDataSetChanged();
        tabs = view.findViewById(R.id.tab_host);
        tabs.setSelectedTabIndicatorColor(getResources().getColor(R.color.colorAccent));
        tabs.setTabTextColors(Color.parseColor("#000000"), getResources().getColor(R.color.colorAccent));

        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case RECIPES_TO_COOK_POSITION:
                        RecipeItemRepository.set(cookingRecipes);
                        recipeAdapter.notifyDataSetChanged();
                        rvRecipes.smoothScrollToPosition(0);
                        return;
                    case RECIPES_CLOSE_TO_COOKING:
                        ArrayList<Recipe> tempRecipes = new ArrayList<>();
                        for (int j = 0; j < 5; j++) {
                            for (int i = 0; i < browsingRecipes.size(); i++) {
                                if (browsingRecipes.get(i).getIngredientsMissing().size() == j) {
                                    tempRecipes.add(browsingRecipes.get(i));
                                }
                            }
                        }
                        browsingRecipes = tempRecipes;
                        RecipeItemRepository.set(browsingRecipes);
                        recipeAdapter.notifyDataSetChanged();
                        rvRecipes.smoothScrollToPosition(0);
                        return;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
        if (getArguments() != null) {
            TabLayout.Tab tab = tabs.getTabAt(getArguments().getInt("tab"));
            tab.select();
            rvRecipes.scrollToPosition(getArguments().getInt("index"));
        }
    }

    public void refreshRecommendedRecipes() {
        ArrayList<Recipe> allRecipes = appendRecipes(cookingRecipes, browsingRecipes);
        cookingRecipes.clear();
        browsingRecipes.clear();
        ArrayList<Food> fridge = FoodItemRepository.getAll();
        RecipeItemRepository.clear();
        ArrayList<ArrayList<Food>> ingredientsMissingFromRecipes = new ArrayList<ArrayList<Food>>();
        //loop over every recipe given
        for (int i = 0; i < allRecipes.size(); i++) {
            allRecipes.get(i).getIngredientsMissing().clear();
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
                if (!isIngredientFound) {
                    //creates list of all ingredients missing for each recipe in allRecipes
                    isRecipeValid = false;
                    recipe.add(recipeIngredients.get(j));
                }
            }
            ingredientsMissingFromRecipes.add(i, recipe);
            if (isRecipeValid) {
                cookingRecipes.add(allRecipes.get(i));
            }
        }
        for (int i = 0; i < ingredientsMissingFromRecipes.size(); i++) {
            if (ingredientsMissingFromRecipes.get(i).size() <= 5 && ingredientsMissingFromRecipes.get(i).size() > 0) {
                browsingRecipes.add(allRecipes.get(i));
                browsingRecipes.get(browsingRecipes.size() - 1).getIngredientsMissing().clear();
                browsingRecipes.get(browsingRecipes.size() - 1).setIngredientsMissing(ingredientsMissingFromRecipes.get(i));
            }
        }
    }

    public ArrayList<Recipe> appendRecipes(ArrayList<Recipe> cooking, ArrayList<Recipe> browsing) {
        ArrayList<Recipe> allRecipes = new ArrayList<>();
        for (int i = 0; i < cooking.size() + browsing.size(); i++) {
            if (i < cooking.size()) {
                allRecipes.add(cooking.get(i));
            } else {
                allRecipes.add(browsing.get(i - cooking.size()));
            }
        }
        return allRecipes;
    }

    public void loadRecommendedRecipes() {
        ArrayList<Recipe> allRecipes = setUpRecipeList();
        ArrayList<Food> fridge = FoodItemRepository.getAll();
        RecipeItemRepository.clear();
        ArrayList<ArrayList<Food>> ingredientsMissingFromRecipes = new ArrayList<>();
        //loop over every recipe given
        for (int i = 0; i < allRecipes.size(); i++) {
            //clear each recipes missing ingredient array here
            allRecipes.get(i).setIngredientsMissing(new ArrayList<Food>()); ///THIS IS DIFF FROM ISABEL'S CHECK IT!!
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
                if (!isIngredientFound) {
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
            if (ingredientsMissingFromRecipes.get(i).size() <= 5 && ingredientsMissingFromRecipes.get(i).size() > 0) {
                browsingRecipes.add(allRecipes.get(i));
                browsingRecipes.get(browsingRecipes.size() - 1).setIngredientsMissing(ingredientsMissingFromRecipes.get(i));
            }
        }
        cookingRecipes = RecipeItemRepository.getAll();
    }

    /**
     * Currently setting this method up with fake data
     * Eventually pull from API or backend
     */
    public ArrayList<Recipe> setUpRecipeList() {
        ArrayList<Recipe> recipes = new ArrayList<>();
        GetRecipes jsonRecipes = new GetRecipes();
        jsonRecipes.execute();
        String jsonRecipesArray = jsonRecipes.line;
        while (jsonRecipesArray == null) {
            jsonRecipesArray = jsonRecipes.line;
        }
        try {
            JSONObject jsonObject = new JSONObject(jsonRecipesArray);
            JSONArray jsonArray = jsonObject.getJSONArray("recipes");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject recipeObject = jsonArray.getJSONObject(i);
                Recipe recipe = new Recipe();
                recipe.setName(recipeObject.getString("name"));
                recipe.setSource(recipeObject.getString("sourceName"));
                recipe.setSourceLink(recipeObject.getString("sourceLink"));
                recipe.setCookTimeHours(recipeObject.getInt("cookTimeHours"));
                recipe.setCookTimeMinutes(recipeObject.getInt("cookTimeMinutes"));
                recipe.setImageUrl(recipeObject.getString("imageUrl"));
                JSONArray ingredients = recipeObject.getJSONArray("ingredients");
                ArrayList<Food> recipeIngredients = new ArrayList<>();
                for (int j = 0; j < ingredients.length(); j++) {
                    String fullIngredient = ingredients.getString(j);
                    Food food = new Food();
                    String[] splitIngredient = fullIngredient.split(" of ", 2);
                    String[] splitQuantity = splitIngredient[0].split(" ", 2);
                    food.setQuantity(Double.parseDouble(splitQuantity[0]));
                    if (splitQuantity[1] != null && splitQuantity[1] != "") {
                        food.setUnit(splitQuantity[1]);
                    }
                    food.setName(splitIngredient[1]);
                    recipeIngredients.add(food);
                }
                recipe.setIngredients(recipeIngredients);
                JSONArray instructions = recipeObject.getJSONArray("instructions");
                ArrayList<String> recipeInstructions = new ArrayList<>();
                for (int k = 0; k < instructions.length(); k++) {
                    recipeInstructions.add(instructions.getString(k));
                }
                recipe.setInstructions(recipeInstructions);
                recipes.add(recipe);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
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

    class GetRecipes extends AsyncTask {
        String line = null;

        @Override
        protected String doInBackground(Object[] objects) {
            URL url = null;
            try {
                url = UrlManager.getRecipeEndpoint();
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                InputStream in = connection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                StringBuilder sb = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
                line = sb.toString();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (line != null) {
                return line;
            }
            return null;
        }
    }
}
