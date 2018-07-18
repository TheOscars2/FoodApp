package me.ivg2.foodapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class EditIngredientListActivity extends AppCompatActivity {

    EditText etItemText;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_ingredient_list);

        etItemText = (EditText) findViewById(R.id.etIngredient);
        etItemText.setText(getIntent().getStringExtra("item_text"));
        position = getIntent().getIntExtra("item_position", 0);
    }

    //prepares and passes updated item text into MainActivity.java at original position
    public void onSaveItem(View v) {
        Intent data = new Intent();

        data.putExtra("item_text", etItemText.getText().toString());
        data.putExtra("item_position", position);
        setResult(RESULT_OK, data);
        finish();
    }
}
