package me.ivg2.foodapp;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.joda.time.DateTime;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import me.ivg2.foodapp.Model.Food;

/**
 * A simple {@link Fragment} subclass.
 */
public class ManualAddFragment extends Fragment {
    @BindView(R.id.etFood)
    EditText etFoodName;
    @BindView(R.id.etFoodQuantity)
    EditText etFoodQuantity;
    @BindView(R.id.etFoodExpirationDate)
    EditText etFoodExpDate;
    @BindView(R.id.addToFridgeBtn)
    Button addToFridge;
    private int index;
    private final int NEW_ENTRY = -2;
    private boolean isEditMode;
    private DateFormat dateFormat;
    private Callback callback;
    private Unbinder unbinder;
    private String unitEntered;
    private boolean isNewBarcode;

    interface Callback {
        void goToFridge();
<<<<<<< HEAD

        void goToDatePicker(int index, String tempName, String tempQuantity);
=======
        void goToBarcodeWithNewFood();
>>>>>>> Recipes and barcodes pulling from backend.
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ManualAddFragment.Callback) {
            callback = (ManualAddFragment.Callback) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        callback = null;
    }

    public ManualAddFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        index = NEW_ENTRY;//default value indicates new Add
        return inflater.inflate(R.layout.fragment_manual_add, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
        dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        try {
            String name = getArguments().getString("productName");
            etFoodName.setText(name);
            index = getArguments().getInt("index", -1);
        } catch (NullPointerException n) {
        }
        if (getArguments() != null && !isNewBarcode) {
            isEditMode = true;
            if (index > -1) {
                etFoodName.setText(FoodItemRepository.get(index).getName());
                etFoodQuantity.setText(Double.toString(FoodItemRepository.get(index).getQuantity()));
                etFoodExpDate.setText(dateFormat.format(FoodItemRepository.get(index).getExpirationDate().toDate()));
            } else {
                isEditMode = false;
                if (index == NEW_ENTRY) {
                    etFoodName.setText(getArguments().getString("tempName"));
                    etFoodQuantity.setText(getArguments().getString("tempQuantity"));
                    etFoodExpDate.setText(getArguments().getString("newExpDate"));
                }
            }
        }
        Spinner dynamicSpinner = view.findViewById(R.id.dynamic_spinner);
        etFoodExpDate.setInputType(InputType.TYPE_NULL);
        etFoodExpDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.goToDatePicker(index, etFoodName.getText().toString(), etFoodQuantity.getText().toString());
            }
        });
        etFoodExpDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getView().getRootView().getWindowToken(), 0);
                    callback.goToDatePicker(index, etFoodName.getText().toString(), etFoodQuantity.getText().toString());
                }
            }
        });
        final String[] items = new String[]{"servings", "gal", "cups", "mL"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, items);
        dynamicSpinner.setAdapter(adapter);
        dynamicSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                unitEntered = items[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getActivity(), "Please fill out all food information", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.addToFridgeBtn)
    public void tryToAddFoodToFridge() {
        if (!validInput()) return;
        GroceryListItemRepository groceryList = GroceryListItemRepository.getInstance();
        Food newFood = null;
        try {
            newFood = new Food(etFoodName.getText().toString(), Double.parseDouble(etFoodQuantity.getText().toString()),
                    new DateTime(dateFormat.parse(etFoodExpDate.getText().toString())));
            newFood.setUnit(unitEntered);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //updates grocery list on addition of new foods; assumes unit match up
        for (Food f : groceryList.getAll()) {
            if (newFood.getName().toLowerCase().equals(f.getName().toLowerCase())) {
                f.setQuantity(f.getQuantity() - newFood.getQuantity());
                if (f.getQuantity() <= 0) {
                    groceryList.delete(groceryList.getIndex(f));
                }
                break;
            }
        }
        if (isEditMode) {
            newFood.setImageURL(FoodItemRepository.get(index).getImageURL());
            FoodItemRepository.update(index, newFood);
        } else {
            FoodItemRepository.create(newFood);
        }

        callback.goToFridge();
    }

    private boolean validInput() {
        //check for empty inputs
        if (etFoodName.getText().length() == 0 || etFoodQuantity.getText().length() == 0 || etFoodExpDate.getText().length() == 0) {
            Toast.makeText(getActivity(), "Please fill out all food information", Toast.LENGTH_SHORT).show();
            return false;
        }
        //check date length
        if (etFoodExpDate.getText().length() != 10) {
            Toast.makeText(getActivity(), "Please enter a valid date (MM/DD/YYYY)", Toast.LENGTH_SHORT).show();
            return false;
        }
        //check for slashes
        if (etFoodExpDate.getText().toString().charAt(2) != '/' || etFoodExpDate.getText().toString().charAt(5) != '/') {
            Toast.makeText(getActivity(), "Please enter a valid date (MM/DD/YYYY)", Toast.LENGTH_SHORT).show();
            return false;
        }
        //check for no bad chars
        if (etFoodExpDate.getText().toString().contains(".") || etFoodExpDate.getText().toString().contains("-")) {
            Toast.makeText(getActivity(), "Please enter a valid date (MM/DD/YYYY)", Toast.LENGTH_SHORT).show();
            return false;
        }
        //check that month and date and year only contain numbers, maybe change to get count of slashes and must equal 2
        if (etFoodExpDate.getText().toString().substring(0, 2).contains("/") || etFoodExpDate.getText().toString().substring(3, 5).contains("/") || etFoodExpDate.getText().toString().substring(6).contains("/")) {
            Toast.makeText(getActivity(), "Please enter a valid date (MM/DD/YYYY)", Toast.LENGTH_SHORT).show();
            return false;
        }
        //check that the month date and year are valid
        if (Integer.parseInt(etFoodExpDate.getText().toString().substring(0, 2)) > 12 || Integer.parseInt(etFoodExpDate.getText().toString().substring(3, 5)) > 31 || Integer.parseInt(etFoodExpDate.getText().toString().substring(6)) < 2000) {
            Toast.makeText(getActivity(), "Please check the date entered is correct", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}

