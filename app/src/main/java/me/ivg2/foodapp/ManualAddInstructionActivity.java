package me.ivg2.foodapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import me.ivg2.foodapp.Model.Recipe;

public class ManualAddInstructionActivity extends AppCompatActivity {

    ArrayList<String> instructions;
    ArrayAdapter<String> instructionsAdapter;
    ListView lvInstructions;
    boolean isEditMode;
    int position;

    public static final int EDIT_REQUEST_CODE = 20;
    public static final String ITEM_TEXT = "itemText";
    public static final String ITEM_POSITION = "itemPosition";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual_add_instruction);

        lvInstructions = findViewById(R.id.lvInstructions);
        instructions = new ArrayList<>();
        instructionsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, instructions);
        lvInstructions.setAdapter(instructionsAdapter);

        isEditMode = true;

        Intent intent = getIntent();
        position = intent.getIntExtra("position", -1);
        if (!(position == -1)) {
            Recipe recipe = RecipeItemRepository.get(position);

            for (String instruction : recipe.getInstructions()) {
                instructions.add(instruction);
                instructionsAdapter.notifyDataSetChanged();
            }
        } else {
            isEditMode = false;
        }

        setupListViewListener();
    }

    public void onAddIngredient(View view) {
        EditText etNewItem = (EditText) findViewById(R.id.etNewInstruction);
        String itemText = etNewItem.getText().toString();
        instructions.add(itemText);
        etNewItem.setText("");

        instructionsAdapter.notifyDataSetChanged();

        Toast.makeText(getApplicationContext(), "Ingredient added to list!", Toast.LENGTH_SHORT).show();
    }

    public void setupListViewListener() {
        lvInstructions.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                instructions.remove(position);
                instructionsAdapter.notifyDataSetChanged();

                return true;
            }
        });

        //set the ListView's regular click listener
        lvInstructions.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent i = new Intent(ManualAddInstructionActivity.this, EditInstructionListActivity.class);
                i.putExtra("item_text", instructions.get(position));
                i.putExtra("item_position", position);
                startActivityForResult(i, EDIT_REQUEST_CODE);
            }
        });
    }

    public void onFinishedInstructions(View v) {
        //OnClick we need to update the last recipe in our singleton class
        Recipe currentRecipe = RecipeItemRepository.get(RecipeItemRepository.size() - 1);

        currentRecipe.setInstructions(instructions);

        if (isEditMode) {
            RecipeItemRepository.update(position, currentRecipe);
            RecipeItemRepository.delete(RecipeItemRepository.size() - 1);
        }

        Intent intent = new Intent(ManualAddInstructionActivity.this, HomeActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK && requestCode == EDIT_REQUEST_CODE) {
            String updatedItem = data.getExtras().getString("item_text");
            int position = data.getExtras().getInt("item_position", 0);
            instructions.set(position, updatedItem);

            instructionsAdapter.notifyDataSetChanged();
        }
    }
}