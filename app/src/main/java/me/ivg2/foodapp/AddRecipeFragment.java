package me.ivg2.foodapp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import me.ivg2.foodapp.Model.Recipe;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddRecipeFragment extends Fragment {
    @BindView(R.id.etRecipeName)
    EditText etRecipeName;
    @BindView(R.id.etRecipeSource)
    EditText etRecipeSource;
    @BindView(R.id.etHrs)
    EditText etHoursField;
    @BindView(R.id.etMins)
    EditText etMinutesField;
    @BindView(R.id.btnAddIng)
    Button addIngBtn;
    private int index;
    private Unbinder unbinder;
    private static Callback callback;
    boolean isEditMode = true;

    interface Callback {
        void goToAddIngredients();

        void goToEditIngredients(int index);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof AddRecipeFragment.Callback) {
            callback = (AddRecipeFragment.Callback) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        callback = null;
    }

    public AddRecipeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_recipe, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        unbinder = ButterKnife.bind(this, view);
        try {
            index = getArguments().getInt("index");
            Recipe recipe = RecipeItemRepository.get(index);
            etRecipeName.setText(recipe.getName());
            etRecipeSource.setText(recipe.getSource());
            etHoursField.setText(String.valueOf(recipe.getCookTimeHours()));
            etMinutesField.setText(String.valueOf(recipe.getCookTimeMinutes()));
        } catch (NullPointerException e) {
            isEditMode = false;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.btnAddIng)
    public void tryToAddRecipe() {
        if (etRecipeName.getText().length() == 0 || etRecipeSource.getText().length() == 0 || etHoursField.getText().length() == 0 || etMinutesField.getText().length() == 0) {
            Toast.makeText(getActivity(), "Please fill out all recipe information", Toast.LENGTH_SHORT).show();
            return;
        }
        Recipe recipe = new Recipe(etRecipeName.getText().toString(), etRecipeSource.getText().toString(),
                Integer.parseInt(etHoursField.getText().toString()), Integer.parseInt(etMinutesField.getText().toString()));
        //add this incomplete recipe to singleton class and complete in next steps
        RecipeItemRepository.create(recipe);
        //On click we also need to push user input to recipe in server
        if (isEditMode) {
            callback.goToEditIngredients(index);
        } else {
            callback.goToAddIngredients();
        }
    }
}
