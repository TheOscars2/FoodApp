package me.ivg2.foodapp;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.joda.time.DateTime;
import org.joda.time.Period;


/**
 * A simple {@link Fragment} subclass.
 */
public class FoodDetailFragment extends Fragment {

    private ImageView ivFoodImage;
    private TextView etFoodName;
    private TextView etFoodQuantity;
    private TextView etFoodExpDate;
    private TextView tvOptions;

    private int index;

    private Callback callback;

    interface Callback {
        void goToFridge();
        void goToEditFood(int index);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof FoodDetailFragment.Callback) {
            callback = (FoodDetailFragment.Callback) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();

        callback = null;
    }

    public FoodDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_food_detail, container, false);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ivFoodImage = view.findViewById(R.id.foodImage);
        etFoodName = view.findViewById(R.id.name);
        etFoodQuantity = view.findViewById(R.id.quantity);
        etFoodExpDate = view.findViewById(R.id.expiration);
        tvOptions = view.findViewById(R.id.options);

        Bundle arguments = getArguments();
        index = arguments.getInt("index");

        etFoodName.setText(FoodItemRepository.get(index).getName());
        etFoodQuantity.setText(Integer.toString(FoodItemRepository.get(index).getQuantity()));

        //setting food expiration date to proper format
        DateTime targetDateTime = FoodItemRepository.get(index).getExpirationDate();
        Period period = new Period(DateTime.now(), targetDateTime);

        if (period.getYears() > 0) {
            etFoodExpDate.setText("Expires in " + period.getYears() + " years");
        } else if (period.getMonths() > 0) {
            etFoodExpDate.setText("Expires in " + period.getMonths() + " months");
        } else {
            etFoodExpDate.setText("Expires in " + period.getDays() + " days");
        }

        String url =FoodItemRepository.get(index).getImageURL();

        if (url != null) {
            Glide.with(this)
                    .load(url)
                    .into(ivFoodImage);
        }

        tvOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu menu = new PopupMenu(getContext(), tvOptions);
                menu.inflate(R.menu.recipe_menu);

                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.delete:
                                FoodItemRepository.delete(index);
                                callback.goToFridge();
                                return true;
                            case R.id.edit:
                                callback.goToEditFood(index);
                                return true;
                            default:
                                return false;
                        }
                    }
                });

                menu.show();
            }
        });
    }
}
