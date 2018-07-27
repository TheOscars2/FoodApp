package me.ivg2.foodapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;
import butterknife.OnItemLongClick;
import me.ivg2.foodapp.Model.Recipe;

public class ManualAddIngredientsActivity extends AppCompatActivity {
    ArrayList<String> ingredients;
    ArrayAdapter<String> ingredientAdapter;
    @BindView(R.id.lvIngredients)
    ListView lvIngredients;
    boolean isEditMode;
    int index;
    public static final int EDIT_REQUEST_CODE = 20;
    public static final String ITEM_TEXT = "itemText";
    public static final String ITEM_POSITION = "itemPosition";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual_add_ingredients);
        ButterKnife.bind(this);
        ingredients = new ArrayList<>();
        ingredientAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, ingredients);
        lvIngredients.setAdapter(ingredientAdapter);
        isEditMode = true;
        Intent intent = getIntent();
        index = intent.getIntExtra("index", -1);
        if (!(index == -1)) {
            Recipe recipe = RecipeItemRepository.get(index);
            for (String ingredient : recipe.getIngredients()) {
                ingredients.add(ingredient);
                ingredientAdapter.notifyDataSetChanged();
            }
        } else {
            isEditMode = false;
        }
    }

    public void onAddIngredient(View view) {
        EditText etNewItem = (EditText) findViewById(R.id.newIngredient);
        String itemText = etNewItem.getText().toString();
        ingredients.add('\u2022' + " " + itemText);
        etNewItem.setText("");
        ingredientAdapter.notifyDataSetChanged();
    }

    public void ingredientsFinished(View v) {
        //On click add object to last recipe in RecipeItemRepository
        RecipeItemRepository recipeItemRepository = RecipeItemRepository.getInstance();
        Recipe currentRecipe = recipeItemRepository.get(recipeItemRepository.size() - 1);
        currentRecipe.setIngredients(ingredients);
        Intent intent = new Intent(ManualAddIngredientsActivity.this, ManualAddInstructionActivity.class);
        //send the user to the next add recipe screen
        if (isEditMode) {
            intent.putExtra("index", index);
        }
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == EDIT_REQUEST_CODE) {
            String updatedItem = data.getExtras().getString("item_text");
            int position = data.getExtras().getInt("item_position", 0);
            ingredients.set(position, updatedItem);
            ingredientAdapter.notifyDataSetChanged();
        }
    }

    @OnItemLongClick(R.id.lvIngredients)
    public boolean deleteTheIngredient(int position) {
        ingredients.remove(position);
        ingredientAdapter.notifyDataSetChanged();
        return true;
    }

    @OnItemClick(R.id.lvIngredients)
    public void editTheIngredient(int position) {
        Intent i = new Intent(ManualAddIngredientsActivity.this, EditInstructionListActivity.class);
        i.putExtra("item_text", ingredients.get(position));
        i.putExtra("item_position", position);
        startActivityForResult(i, EDIT_REQUEST_CODE);
    }
}
