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

import me.ivg2.foodapp.Model.Recipe;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.ViewHolder> {

    private RecipeItemRepository recipes;
    Context context;


    public RecipeAdapter(RecipeItemRepository recipes) {
        this.recipes = recipes;
    }

    @NonNull
    @Override
    public RecipeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View recipeView = inflater.inflate(R.layout.item_recipe, viewGroup, false);
        RecipeAdapter.ViewHolder view = new RecipeAdapter.ViewHolder(recipeView);
        return view;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.tvOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu menu = new PopupMenu(context, holder.tvOptions);
                menu.inflate(R.menu.recipe_menu);

                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.delete:
                                recipes.delete(position);
                                notifyDataSetChanged();
                                return true;
                            case R.id.edit:
                                RecipeFragment.onEditClicked(position);
                                return true;
                            default:
                                return false;
                        }
                    }
                });

                menu.show();
            }
        });

        Recipe recipe = recipes.get(position);

        holder.tvRecipeName.setText(recipe.getName());
        holder.tvRecipeSource.setText(recipe.getSource());
        holder.tvHourTime.setText("" + recipe.getCookTimeHours());
        holder.tvMinTime.setText("" + recipe.getCookTimeMinutes());

        if (recipe.getImageBitmap() != null) {
            holder.ivRecipeImage.setImageBitmap(recipe.getImageBitmap());
        } else {
            if (recipe.getImageUrl() != null) {
                Glide.with(context)
                        .load(recipe.getImageUrl())
                        .into(holder.ivRecipeImage);
            }
        }
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView ivRecipeImage;
        TextView tvRecipeName;
        TextView tvHourTime;
        TextView tvMinTime;
        TextView tvRecipeSource;
        TextView tvOptions;

        public ViewHolder(View itemView) {
            super(itemView);

            ivRecipeImage = itemView.findViewById(R.id.recipeImage);
            tvRecipeName = itemView.findViewById(R.id.recipeName);
            tvHourTime = itemView.findViewById(R.id.hourTime);
            tvMinTime = itemView.findViewById(R.id.minTime);
            tvRecipeSource = itemView.findViewById(R.id.recipeSource);
            tvOptions = itemView.findViewById(R.id.options);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();

            if (position != RecyclerView.NO_POSITION) {
                Recipe recipe = recipes.get(position);
                RecipeFragment.onRecipeClicked(recipe, position);
            }
        }
    }
}
