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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import me.ivg2.foodapp.Model.Food;

/**
 * A simple {@link Fragment} subclass.
 */
public class ManualAddFragment extends Fragment {

    private EditText etFoodName;
    private EditText etFoodQuantity;
    private EditText etFoodExpDate;
    private Button addToFridge;

    private int index;
    private boolean isEditMode;
    private DateFormat dateFormat;

    private Callback callback;

    interface Callback {
        void goToFridge();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof ManualAddFragment.Callback) {
            callback = (ManualAddFragment.Callback) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();

        callback = null;
    }

    public ManualAddFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_manual_add, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        etFoodName = view.findViewById(R.id.etFood);
        etFoodQuantity = view.findViewById(R.id.etFoodQuantity);
        etFoodExpDate = view.findViewById(R.id.etFoodExpirationDate);
        addToFridge = view.findViewById(R.id.addToFridgeBtn);

        dateFormat = new SimpleDateFormat("mm/dd/yyyy");

        try {
            String name = getArguments().getString("productName");
            etFoodName.setText(name);
        } catch (NullPointerException n) {
        }

        isEditMode = true;
        index = getArguments().getInt("index", -1);

        if (!(index == -1)) {
            etFoodName.setText(FoodItemRepository.get(index).getName());
            etFoodQuantity.setText(Double.toString(FoodItemRepository.get(index).getQuantity()));
            etFoodExpDate.setText(dateFormat.format(FoodItemRepository.get(index).getExpirationDate()));
        } else {
            isEditMode = false;
        }

        addToFridge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Food newFood = null;
                try {
                    newFood = new Food(etFoodName.getText().toString(), Double.parseDouble(etFoodQuantity.getText().toString()),
                            dateFormat.parse(etFoodExpDate.getText().toString()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                if (isEditMode) {
                    newFood.setImageURL(FoodItemRepository.get(index).getImageURL());
                    FoodItemRepository.update(index, newFood);
                } else {
                    FoodItemRepository.create(newFood);
                }

                callback.goToFridge();
            }
        });
    }
}
