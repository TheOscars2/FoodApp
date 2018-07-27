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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
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
