package me.ivg2.foodapp;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
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

import java.util.ArrayList;
import java.util.Collections;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.ivg2.foodapp.Model.Food;

public class FridgeGridAdapter extends RecyclerView.Adapter<FridgeGridAdapter.ViewHolder> {
    private FoodItemRepository foods;
    private ArrayList<Food> listOfFoodObjects;
    private ArrayList<Food> foodListCopy;
    Context context;

    public FridgeGridAdapter(FoodItemRepository foodRepo) {
        this.foods = foodRepo;
        this.listOfFoodObjects = foodRepo.getData();
        this.foodListCopy = foodRepo.getData();
    }

    @NonNull
    @Override
    public FridgeGridAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View foodView = inflater.inflate(R.layout.item_food, viewGroup, false);
        FridgeGridAdapter.ViewHolder view = new FridgeGridAdapter.ViewHolder(foodView);
        return view;
    }

    @Override
    public void onBindViewHolder(@NonNull final FridgeGridAdapter.ViewHolder viewHolder, final int i) {
        viewHolder.options.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu menu = new PopupMenu(context, viewHolder.options);
                menu.inflate(R.menu.recipe_menu);
                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.delete:
                                foods.delete(foods.getAll().indexOf(listOfFoodObjects.get(i)));
                                listOfFoodObjects.remove(i);
                                //maybe re get listOfFoodObjects
                                notifyDataSetChanged();
                                return true;
                            case R.id.edit:
                                FridgeFragment.onEditFoodClicked(i);
                                return true;
                            default:
                                return false;
                        }
                    }
                });
                menu.show();
            }
        });
        Collections.sort(listOfFoodObjects, Food.ALPHABETICAL);
        Food food = listOfFoodObjects.get(i);
        viewHolder.foodName.setText(capitalize(food.getName()));
        int[] colors = {
                ContextCompat.getColor(context, R.color.Brown2),
                ContextCompat.getColor(context, R.color.colorPrimary),
                ContextCompat.getColor(context, R.color.Orange5),
                ContextCompat.getColor(context, R.color.Orange4),
                ContextCompat.getColor(context, R.color.Orange3),
                ContextCompat.getColor(context, R.color.Orange2),
                ContextCompat.getColor(context, R.color.Orange1)};

        //setting food expiration date to proper format
        DateTime targetDateTime = food.getExpirationDate();

        //get today
        DateTime rightNow = DateTime.now();

        //get differences
        int yearsToExpire = Years.yearsBetween(rightNow, targetDateTime).getYears();
        int monthsToExpire = Months.monthsBetween(rightNow, targetDateTime).getMonths();
        int daysToExpire = Days.daysBetween(rightNow, targetDateTime).getDays();
        int weeksToExpire = Weeks.weeksBetween(rightNow, targetDateTime).getWeeks();

        if (food.getName().length() > 2) {
            viewHolder.foodImage.setText(capitalize(food.getName()).substring(0, 2));
            viewHolder.foodImage.setTextColor(Color.parseColor("#ffffff"));
        }

        //set background color based on exp date
        if (daysToExpire < 0) {
            viewHolder.foodImage.setBackgroundColor(colors[0]);
        } else if (daysToExpire < 2) {
            viewHolder.foodImage.setBackgroundColor(colors[1]);
        } else if (weeksToExpire < 2) {
            viewHolder.foodImage.setBackgroundColor(colors[2]);
        } else if (monthsToExpire < 1) {
            viewHolder.foodImage.setBackgroundColor(colors[3]);
        } else if (monthsToExpire < 3) {
            viewHolder.foodImage.setBackgroundColor(colors[4]);
        } else if (monthsToExpire < 6) {
            viewHolder.foodImage.setBackgroundColor(colors[5]);
        } else {
            viewHolder.foodImage.setBackgroundColor(colors[6]);
        }

    }

    public String capitalize(String name) {
        String[] split =  name.split(" ");
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
    public int getItemCount() {
        return listOfFoodObjects.size();
    }

    public void filter(String searchText) {
        if (searchText.isEmpty()) {
            listOfFoodObjects.clear();
            listOfFoodObjects.addAll(foods.getData());
        } else {
            ArrayList<Food> searchResult = new ArrayList<>();
            searchText = searchText.toLowerCase();
            for (Food foodItem : foods.getData()) {
                if (foodItem.getName().toLowerCase().contains(searchText)) {
                    searchResult.add(foodItem);
                }
            }
            listOfFoodObjects.clear();
            listOfFoodObjects.addAll(searchResult);
        }
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.name)
        TextView foodName;
        @BindView(R.id.options)
        TextView options;
        @BindView(R.id.imageFood)
        TextView foodImage;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int index = getAdapterPosition();
            if (index != RecyclerView.NO_POSITION) {
                FridgeFragment.onFoodViewClicked(index);
            }
        }
    }

    public ArrayList<Food> getListOfFoodObjects() {
        return listOfFoodObjects;
    }
}


