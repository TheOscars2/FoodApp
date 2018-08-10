package me.ivg2.foodapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Months;
import org.joda.time.Weeks;
import org.joda.time.Years;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class FoodDetailFragment extends Fragment {
    @BindView(R.id.foodImage)
    TextView tvFoodImage;
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
        etFoodName.setText(capitalize(FoodItemRepository.get(index).getName()));
        etFoodQuantity.setText(Double.toString(FoodItemRepository.get(index).getQuantity()) + " " + FoodItemRepository.get(index).getUnit());
        final int[] colors = {
                ContextCompat.getColor(getContext(), R.color.Brown2),
                ContextCompat.getColor(getContext(), R.color.colorPrimary),
                ContextCompat.getColor(getContext(), R.color.Orange5),
                ContextCompat.getColor(getContext(), R.color.Orange4),
                ContextCompat.getColor(getContext(), R.color.Orange3),
                ContextCompat.getColor(getContext(), R.color.Orange2),
                ContextCompat.getColor(getContext(), R.color.Orange1),
        };

        //setting food expiration date to proper format
        DateTime targetDateTime = FoodItemRepository.get(index).getExpirationDate();

        //get today
        DateTime rightNow = DateTime.now();

        //get differences
        int yearsToExpire = Years.yearsBetween(rightNow, targetDateTime).getYears();
        int monthsToExpire = Months.monthsBetween(rightNow, targetDateTime).getMonths();
        int daysToExpire = Days.daysBetween(rightNow, targetDateTime).getDays();
        int weeksToExpire = Weeks.weeksBetween(rightNow, targetDateTime).getWeeks();

        //set exp date text
        if (yearsToExpire > 1) {
            etFoodExpDate.setText("Expires in " + yearsToExpire + " years");
        } else if (yearsToExpire == 1) {
            etFoodExpDate.setText("Expires in " + yearsToExpire + " year");
        } else if (monthsToExpire > 1) {
            etFoodExpDate.setText("Expires in " + monthsToExpire + " months");
        } else if (monthsToExpire == 1) {
            etFoodExpDate.setText("Expires in " + monthsToExpire + " month");
        } else if (weeksToExpire > 1) {
            etFoodExpDate.setText("Expires in " + weeksToExpire + " weeks");
        } else if (daysToExpire == 0) {
            etFoodExpDate.setText("Expires tomorrow!");
        } else if (daysToExpire < 0) {
            etFoodExpDate.setText("ITEM IS EXPIRED");
        } else if (daysToExpire == 1) {
            etFoodExpDate.setText("Expires in " + daysToExpire + " day");
        } else {
            etFoodExpDate.setText("Expires in " + daysToExpire + " days");
        }

        //set name abbreviation
        if (FoodItemRepository.get(index).getName().length() > 2) {
            tvFoodImage.setText(capitalize(FoodItemRepository.get(index).getName()).substring(0, 2));
            tvFoodImage.setTextColor(Color.parseColor("#ffffff"));
        }

        //set background color based on exp date
        if (daysToExpire < 0) {
            tvFoodImage.setBackgroundColor(colors[0]);
        } else if (daysToExpire < 2) {
            tvFoodImage.setBackgroundColor(colors[1]);
        } else if (weeksToExpire < 2) {
            tvFoodImage.setBackgroundColor(colors[2]);
        } else if (monthsToExpire < 1) {
            tvFoodImage.setBackgroundColor(colors[3]);
        } else if (monthsToExpire < 3) {
            tvFoodImage.setBackgroundColor(colors[4]);
        } else if (monthsToExpire < 6) {
            tvFoodImage.setBackgroundColor(colors[5]);
        } else {
            tvFoodImage.setBackgroundColor(colors[6]);
        }
    }

    public String capitalize(String name) {
        String[] split = name.split(" ");
        StringBuilder builder = new StringBuilder();
        for (String item : split) {
            builder.append(item.substring(0, 1).toUpperCase());
            builder.append(item.substring(1, item.length()));
            builder.append(" ");
        }
        String capitalized = builder.toString();
        if (capitalized.length() > 1) {
            capitalized = capitalized.substring(0, capitalized.length() - 1);
        }
        return capitalized;
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
