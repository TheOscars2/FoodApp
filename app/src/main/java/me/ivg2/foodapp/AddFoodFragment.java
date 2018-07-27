package me.ivg2.foodapp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddFoodFragment extends Fragment {
    private Callback callback;
    @BindView(R.id.barcodeBtn)
    CardView barcodeBtn;
    @BindView(R.id.pluBtn)
    CardView pluBtn;
    @BindView(R.id.manualAddBtn)
    CardView manualAddBtn;
    private Unbinder unbinder;

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
        unbinder = ButterKnife.bind(this, view);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.barcodeBtn)
    public void goToBCScan() {
        callback.goToBarcodeScanner();
    }

    @OnClick(R.id.pluBtn)
    public void goToPlu() {
        callback.goToPLUInput();
    }

    @OnClick(R.id.manualAddBtn)
    public void goToManAdd() {
        callback.goToManualFoodAddition();
    }
}