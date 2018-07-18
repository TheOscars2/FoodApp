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

import me.ivg2.foodapp.Model.Food;

public class FridgeGridAdapter extends RecyclerView.Adapter<FridgeGridAdapter.ViewHolder>{
    private FoodItemRepository foods;
    Context context;


    public FridgeGridAdapter(FoodItemRepository foods) {
        this.foods = foods;
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
                                foods.delete(i);
                                notifyDataSetChanged();
                                return true;
                            case R.id.edit:
                                return true;
                            default:
                                return false;
                        }
                    }
                });
                menu.show();
            }
        });

        Food food = foods.get(i);

        viewHolder.foodName.setText(food.getName());

        if (food.getImageURL() != null) {
            Glide.with(context)
                    .load(food.getImageURL())
                    .into(viewHolder.foodImage);
        }
    }

    @Override
    public int getItemCount() {
        return foods.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
       ImageView foodImage;
       TextView foodName;
       TextView options;

        public ViewHolder(View itemView) {
            super(itemView);

            foodName = itemView.findViewById(R.id.name);
            foodImage = itemView.findViewById(R.id.imageFood);
            options = itemView.findViewById(R.id.options);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();

            if (position != RecyclerView.NO_POSITION) {
                Food food = foods.get(position);
                FridgeFragment.onFoodViewClicked(food);
            }
        }
    }
}


