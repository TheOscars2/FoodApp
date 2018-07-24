package me.ivg2.foodapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

import me.ivg2.foodapp.Model.Recipe;

public class ManualAddIngredientsActivity extends AppCompatActivity {

    ArrayList<String> ingredients;
    ArrayAdapter<String> ingredientAdapter;
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

        lvIngredients = findViewById(R.id.lvIngredients);
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

        setupListViewListener();
    }

    public void onAddIngredient(View view) {
        EditText etNewItem = (EditText) findViewById(R.id.newIngredient);
        String itemText = etNewItem.getText().toString();
        ingredients.add('\u2022' + " " + itemText);
        etNewItem.setText("");

        ingredientAdapter.notifyDataSetChanged();
    }

    public void setupListViewListener() {
        lvIngredients.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                ingredients.remove(position);
                ingredientAdapter.notifyDataSetChanged();

                return true;
            }
        });

        //set the ListView's regular click listener
        lvIngredients.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent i = new Intent(ManualAddIngredientsActivity.this, EditInstructionListActivity.class);
                i.putExtra("item_text", ingredients.get(position));
                i.putExtra("item_position", position);
                startActivityForResult(i, EDIT_REQUEST_CODE);
            }
        });
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

        if(resultCode == RESULT_OK && requestCode == EDIT_REQUEST_CODE) {
            String updatedItem = data.getExtras().getString("item_text");
            int position = data.getExtras().getInt("item_position", 0);
            ingredients.set(position, updatedItem);

            ingredientAdapter.notifyDataSetChanged();
        }
    }
}
