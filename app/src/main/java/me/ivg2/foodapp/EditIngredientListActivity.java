package me.ivg2.foodapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EditIngredientListActivity extends AppCompatActivity {
    @BindView(R.id.etIngredient)
    EditText etItemText;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_ingredient_list);
        ButterKnife.bind(this);
        etItemText.setText(getIntent().getStringExtra("item_text"));
        position = getIntent().getIntExtra("item_position", 0);
    }

    //prepares and passes updated item text into SplashActivity.java at original position
    public void onSaveItem(View v) {
        Intent data = new Intent();
        data.putExtra("item_text", etItemText.getText().toString());
        data.putExtra("item_position", position);
        setResult(RESULT_OK, data);
        finish();
    }
}
