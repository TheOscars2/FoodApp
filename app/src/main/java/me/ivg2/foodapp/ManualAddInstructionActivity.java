package me.ivg2.foodapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;
import butterknife.OnItemLongClick;
import me.ivg2.foodapp.Model.Recipe;

public class ManualAddInstructionActivity extends AppCompatActivity {
    ArrayList<String> instructions;
    ArrayAdapter<String> instructionsAdapter;
    @BindView(R.id.lvInstructions)
    ListView lvInstructions;
    boolean isEditMode;
    int index;
    public static final int EDIT_REQUEST_CODE = 20;
    public static final String ITEM_TEXT = "itemText";
    public static final String ITEM_POSITION = "itemPosition";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual_add_instruction);
        ButterKnife.bind(this);
        instructions = new ArrayList<>();
        instructionsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, instructions);
        lvInstructions.setAdapter(instructionsAdapter);
        isEditMode = true;
        Intent intent = getIntent();
        index = intent.getIntExtra("index", -1);
        if (!(index == -1)) {
            Recipe recipe = RecipeItemRepository.get(index);
            for (String instruction : recipe.getInstructions()) {
                instructions.add(instruction);
                instructionsAdapter.notifyDataSetChanged();
            }
        } else {
            isEditMode = false;
        }
    }

    public void onAddInstruction(View view) {
        EditText etNewItem = (EditText) findViewById(R.id.etNewInstruction);
        String itemText = etNewItem.getText().toString();
        instructions.add('\u2022' + " " + itemText);
        etNewItem.setText("");
        instructionsAdapter.notifyDataSetChanged();
        Toast.makeText(getApplicationContext(), "Instruction added to list!", Toast.LENGTH_SHORT).show();
    }

    public void onFinishedInstructions(View v) {
        //OnClick we need to update the last recipe in our singleton class
        Recipe currentRecipe = RecipeItemRepository.get(RecipeItemRepository.size() - 1);
        currentRecipe.setInstructions(instructions);
        if (isEditMode) {
            currentRecipe.setImageUrl(RecipeItemRepository.get(index).getImageUrl());
            currentRecipe.setImageBitmap(RecipeItemRepository.get(index).getImageBitmap());
            RecipeItemRepository.update(index, currentRecipe);
            RecipeItemRepository.delete(RecipeItemRepository.size() - 1);
        }
        Intent intent = new Intent(ManualAddInstructionActivity.this, HomeActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == EDIT_REQUEST_CODE) {
            String updatedItem = data.getExtras().getString("item_text");
            int position = data.getExtras().getInt("item_position", 0);
            instructions.set(position, updatedItem);
            instructionsAdapter.notifyDataSetChanged();
        }
    }

    @OnItemLongClick(R.id.lvInstructions)
    public boolean deleteTheInstruction(int position) {
        instructions.remove(position);
        instructionsAdapter.notifyDataSetChanged();
        return true;
    }

    @OnItemClick(R.id.lvInstructions)
    public void editTheInstruction(int position) {
        Intent i = new Intent(ManualAddInstructionActivity.this, EditInstructionListActivity.class);
        i.putExtra("item_text", instructions.get(position));
        i.putExtra("item_position", position);
        startActivityForResult(i, EDIT_REQUEST_CODE);
    }
}