package me.ivg2.foodapp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import android.widget.Button;
import android.widget.EditText;
import java.util.Date;
import me.ivg2.foodapp.Model.Food;

/**
 * A simple {@link Fragment} subclass.
 */
public class ManualAddFragment extends Fragment {

    private EditText etFoodName;
    private EditText etFoodQuantity;
    private EditText etFoodExpDate;
    private Button addToFridge;

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

        try {
            String name = getArguments().getString("productName");
            etFoodName.setText(name);
        } catch (NullPointerException n) {
            //Toast.makeText(getContext(), "failed", Toast.LENGTH_LONG).show();

        }

        addToFridge.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Food newFood = new Food(etFoodName.getText().toString(), Double.parseDouble(etFoodQuantity.getText().toString()),
                        new Date(etFoodExpDate.getText().toString()));
                FoodItemRepository.create(newFood);

                callback.goToFridge();
            }
        });

    }
}
