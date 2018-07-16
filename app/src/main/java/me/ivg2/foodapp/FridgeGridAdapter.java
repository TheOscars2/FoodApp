package me.ivg2.foodapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import me.ivg2.foodapp.Model.Food;

public class FridgeGridAdapter extends RecyclerView.Adapter<FridgeGridAdapter.ViewHolder>{
    private FoodItemRepository foods;
    Context context;


    public FridgeGridAdapter(FoodItemRepository posts) {
        this.foods = posts;
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
    public void onBindViewHolder(@NonNull FridgeGridAdapter.ViewHolder viewHolder, int i) {
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

        public ViewHolder(View itemView) {
            super(itemView);

            foodName = itemView.findViewById(R.id.name);
            foodImage = itemView.findViewById(R.id.imageFood);

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


