package me.ivg2.foodapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

/**
 * A simple {@link Fragment} subclass.
 */
public class ManualAddFragment extends Fragment {
    private String name;
    private EditText etFood;
    private EditText etFoodQuantity;
    private EditText etFoodExp;
    private Button addToFridgeBtn;

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
        etFood = view.findViewById(R.id.etFood);
        etFoodQuantity = view.findViewById(R.id.etFoodQuantity);
        etFoodExp = view.findViewById(R.id.etFoodExpirationDate);
        addToFridgeBtn = view.findViewById(R.id.addToFridgeBtn);
        try {
            name = getArguments().getString("produceName", "");
        } catch (NullPointerException n) {
            name ="";
        }
        etFood.setText(name);
    }

    public static ManualAddFragment newInstance(String produceName) {
        ManualAddFragment manFrag = new ManualAddFragment();
        Bundle args = new Bundle();
        args.putString("produceName", produceName);
        manFrag.setArguments(args);
        return manFrag;
    }
}
