package me.ivg2.foodapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.ivg2.foodapp.Model.Food;

public class GroceryListAdapter extends RecyclerView.Adapter<GroceryListAdapter.ViewHolder> {
    private GroceryListItemRepository groceryList;
    Context context;

    public GroceryListAdapter(GroceryListItemRepository groceries) {
        groceryList = groceries;
    }

    @NonNull
    @Override
    public GroceryListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View recipeView = inflater.inflate(R.layout.item_grocery_list, viewGroup, false);
        GroceryListAdapter.ViewHolder view = new GroceryListAdapter.ViewHolder(recipeView);
        return view;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int index) {
        Food groceryItem = groceryList.get(index);
        holder.tvFoodName.setText(groceryItem.getName());
        holder.tvFoodQuantity.setText(Integer.toString(groceryItem.getQuantity()));
        holder.tvFoodUnit.setText(groceryItem.getUnit());
    }

    @Override
    public int getItemCount() {
        return groceryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.groceryFoodName)
        TextView tvFoodName;
        @BindView(R.id.groceryFoodQuantity)
        TextView tvFoodQuantity;
        @BindView(R.id.groceryFoodUnit)
        TextView tvFoodUnit;
        @BindView(R.id.checkBox)
        CheckBox checkBox;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
