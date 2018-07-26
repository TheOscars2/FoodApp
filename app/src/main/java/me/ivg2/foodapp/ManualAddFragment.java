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

import org.joda.time.DateTime;

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
        dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        try {
            String name = getArguments().getString("productName");
            etFoodName.setText(name);
        } catch (NullPointerException n) {
        }
        if (getArguments() != null) {
            isEditMode = true;
            index = getArguments().getInt("index", -1);
            if (!(index == -1)) {
                etFoodName.setText(FoodItemRepository.get(index).getName());
                etFoodQuantity.setText(Double.toString(FoodItemRepository.get(index).getQuantity()));
                etFoodExpDate.setText(dateFormat.format(FoodItemRepository.get(index).getExpirationDate().toDate()));
            } else {
                isEditMode = false;
            }
        }
        addToFridge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //check for empty inputs
                if (etFoodName.getText().length() == 0 || etFoodQuantity.getText().length() == 0 || etFoodExpDate.getText().length() == 0) {
                    Toast.makeText(getActivity(), "Please fill out all food information", Toast.LENGTH_SHORT).show();
                    return;
                }
                //check date length
                if (etFoodExpDate.getText().length() != 10) {
                    Toast.makeText(getActivity(), "Please enter a valid date (MM/DD/YYYY)", Toast.LENGTH_SHORT).show();
                    return;
                }
                //check for slashes
                if (etFoodExpDate.getText().toString().charAt(2) != '/' || etFoodExpDate.getText().toString().charAt(5) != '/') {
                    Toast.makeText(getActivity(), "Please enter a valid date (MM/DD/YYYY)", Toast.LENGTH_SHORT).show();
                    return;
                }
                //check for no bad chars
                if (etFoodExpDate.getText().toString().contains(".") || etFoodExpDate.getText().toString().contains("-")) {
                    Toast.makeText(getActivity(), "Please enter a valid date (MM/DD/YYYY)", Toast.LENGTH_SHORT).show();
                    return;
                }
                //check that month and date and year only contain numbers, maybe change to get count of slashes and must equal 2
                if (etFoodExpDate.getText().toString().substring(0, 2).contains("/") || etFoodExpDate.getText().toString().substring(3, 5).contains("/") || etFoodExpDate.getText().toString().substring(6).contains("/")) {
                    Toast.makeText(getActivity(), "Please enter a valid date (MM/DD/YYYY)", Toast.LENGTH_SHORT).show();
                    return;
                }
                //check that the month date and year are valid
                if (Integer.parseInt(etFoodExpDate.getText().toString().substring(0, 2)) > 12 || Integer.parseInt(etFoodExpDate.getText().toString().substring(3, 5)) > 31 || Integer.parseInt(etFoodExpDate.getText().toString().substring(6)) < 2000) {
                    Toast.makeText(getActivity(), "Please check the date entered is correct", Toast.LENGTH_SHORT).show();
                    return;
                }
                Food newFood = null;
                try {
                    newFood = new Food(etFoodName.getText().toString(), Double.parseDouble(etFoodQuantity.getText().toString()),
                            new DateTime(dateFormat.parse(etFoodExpDate.getText().toString())));
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
