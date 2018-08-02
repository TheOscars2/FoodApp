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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class FoodDetailFragment extends Fragment {
    @BindView(R.id.foodImage)
    ImageView ivFoodImage;
    @BindView(R.id.name)
    TextView etFoodName;
    @BindView(R.id.quantity)
    TextView etFoodQuantity;
    @BindView(R.id.expiration)
    TextView etFoodExpDate;
    @BindView(R.id.options)
    TextView tvOptions;
    private Unbinder unbinder;
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
        unbinder = ButterKnife.bind(this, view);
        Bundle arguments = getArguments();
        index = arguments.getInt("index");
        etFoodName.setText(FoodItemRepository.get(index).getName());
        etFoodQuantity.setText(Double.toString(FoodItemRepository.get(index).getQuantity()));
        

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
        String url = FoodItemRepository.get(index).getImageURL();
        if (url != null) {
            Glide.with(this)
                    .load(url)
                    .into(ivFoodImage);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.options)
    public void optionMenuClicks() {
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
}
