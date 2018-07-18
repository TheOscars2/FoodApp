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

import me.ivg2.foodapp.Model.Recipe;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecipeFragment extends Fragment {

    FloatingActionButton floatingAddButton;

    RecipeItemRepository recipes;
    private RecipeAdapter recipeAdapter;
    private RecyclerView rvRecipes;

    interface Callback {
        void goToRecipeDetail(Recipe recipe, int position);
        void goToAddRecipe();
        void goToEditRecipe(int position);
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

        rvRecipes.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvRecipes.setAdapter(recipeAdapter);
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

    public static void onRecipeClicked(Recipe recipe, int position) {
        callback.goToRecipeDetail(recipe, position);
    }

    public static void onEditClicked(int position) {
        callback.goToEditRecipe(position);
    }
}
