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


/**
 * A simple {@link Fragment} subclass.
 */
public class AddFoodFragment extends Fragment {
    private Callback callback;
    private Button barcodeBtn;
    private Button pluBtn;
    private Button manualAddBtn;

    interface Callback {
        void goToBarcodeScanner();
        void goToPLUInput();
        void goToManualFoodAddition();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof AddFoodFragment.Callback) {
            callback = (Callback) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();

        callback = null;
    }

    public AddFoodFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_food, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        barcodeBtn = view.findViewById(R.id.barcodeBtn);
        pluBtn = view.findViewById(R.id.pluBtn);
        manualAddBtn = view.findViewById(R.id.manualAddBtn);

        barcodeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callback.goToBarcodeScanner();
            }
        });
        pluBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callback.goToPLUInput();
            }
        });

        manualAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callback.goToManualFoodAddition();
            }
        });
    }
}