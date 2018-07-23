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

import me.ivg2.foodapp.Model.Recipe;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddRecipeFragment extends Fragment {

    private EditText etRecipeName;
    private EditText etRecipeSource;
    private EditText etHoursField;
    private EditText etMinutesField;
    private Button addIngBtn;
    private int position;

    private static Callback callback;

    interface Callback {
        void goToAddIngredients();
        void goToEditIngredients(int position);
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
        etRecipeName = view.findViewById(R.id.etRecipeName);
        etRecipeSource = view.findViewById(R.id.etRecipeSource);
        etHoursField = view.findViewById(R.id.etHrs);
        etMinutesField = view.findViewById(R.id.etMins);

        boolean isEditMode = true;

        try {
            position = getArguments().getInt("position");
            Recipe recipe = RecipeItemRepository.get(position);

            etRecipeName.setText(recipe.getName());
            etRecipeSource.setText(recipe.getSource());
            etHoursField.setText(String.valueOf(recipe.getCookTimeHours()));
            etMinutesField.setText(String.valueOf(recipe.getCookTimeMinutes()));
        } catch (NullPointerException e) {
            isEditMode = false;
        }

        addIngBtn = view.findViewById(R.id.btnAddIng);

        final boolean finalIsEditMode = isEditMode;
        addIngBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Recipe recipe = new Recipe(etRecipeName.getText().toString(), etRecipeSource.getText().toString(),
                        Integer.parseInt(etHoursField.getText().toString()), Integer.parseInt(etMinutesField.getText().toString()));

                //add this incomplete recipe to singleton class and complete in next steps
                RecipeItemRepository.create(recipe);
                //On click we also need to push user input to recipe in server
                if (finalIsEditMode) {
                    callback.goToEditIngredients(position);
                } else {
                    callback.goToAddIngredients();
                }
            }
        });

    }
}
