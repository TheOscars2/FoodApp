package me.ivg2.foodapp;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;


/**
 * A simple {@link Fragment} subclass.
 */
public class FoodDetailFragment extends Fragment {

    private ImageView ivFoodImage;
    private TextView etFoodName;
    private TextView etFoodQuantity;
    private TextView etFoodExpDate;

    public FoodDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_food_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ivFoodImage = view.findViewById(R.id.foodImage);
        etFoodName = view.findViewById(R.id.name);
        etFoodQuantity = view.findViewById(R.id.quantity);
        etFoodExpDate = view.findViewById(R.id.expiration);

        Bundle arguments = getArguments();

        etFoodName.setText(arguments.getString("name"));
        etFoodQuantity.setText(arguments.getString("quantity"));
        etFoodExpDate.setText(arguments.getString("expiration_date"));

        String url = arguments.getString("image_url");

        if (url != null) {
            Glide.with(this)
                    .load(url)
                    .into(ivFoodImage);
        }
    }
}
