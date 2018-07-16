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
import android.widget.ImageButton;


/**
 * A simple {@link Fragment} subclass.
 */
public class RecipeFragment extends Fragment {

    ImageButton addButton;

    interface Callback {
        void goToRecipeDetail();
        void goToAddRecipe();

        void goToBarcodeScanner();
    }

    private Callback callback;


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
        addButton = view.findViewById(R.id.addRecipe);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callback.goToAddRecipe();
            }
        });

        Button button = view.findViewById(R.id.recipeDetail);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //communicate between fragments
                callback.goToRecipeDetail();
            }
        });
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
}
