package me.ivg2.foodapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.ivg2.foodapp.Model.Food;

public class MissingIngredientsAdapter extends RecyclerView.Adapter<MissingIngredientsAdapter.ViewHolder> {
    private ArrayList<Food> ingredients;
    Context context;

    public MissingIngredientsAdapter(ArrayList<Food> ingredients) {
        this.ingredients = ingredients;
    }

    @NonNull
    @Override
    public MissingIngredientsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View recipeView = inflater.inflate(R.layout.simple_text, viewGroup, false);
        MissingIngredientsAdapter.ViewHolder view = new MissingIngredientsAdapter.ViewHolder(recipeView);
        return view;
    }

    @Override
    public void onBindViewHolder(@NonNull final MissingIngredientsAdapter.ViewHolder holder, final int index) {
        Food food = ingredients.get(index);
        holder.tvMissingIngredient.setText(food.getName());
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.simple_text)
        TextView tvMissingIngredient;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int index = getAdapterPosition();
            if (index != RecyclerView.NO_POSITION) {
                Food toAddtoGroceryList = new Food(ingredients.get(index));
                GroceryListItemRepository.create(toAddtoGroceryList);
                Toast.makeText(context, ingredients.get(index).getName() + " added to grocery list", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
