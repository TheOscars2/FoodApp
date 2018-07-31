package me.ivg2.foodapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

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
        //Collections.sort(listOfFoodObjects, Food.ALPHABETICAL);
        Food food = listOfFoodObjects.get(i);
        viewHolder.foodName.setText(food.getName());
        if (food.getImageURL() != null) {
            Glide.with(context)
                    .load(food.getImageURL())
                    .into(viewHolder.foodImage);
        }
        if (food.getImageURL() == null) {
            Glide.with(context)
                    .load(R.drawable.fooditemplaceholder)
                    .into(viewHolder.foodImage);
        }
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
        @BindView(R.id.imageFood)
        ImageView foodImage;
        @BindView(R.id.name)
        TextView foodName;
        @BindView(R.id.options)
        TextView options;

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


