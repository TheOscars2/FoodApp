package me.ivg2.foodapp;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

import org.joda.time.DateTime;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {
    private DatePickerFragment.Callback callback;
    FoodItemRepository foods;

    interface Callback {
        void goToEditFoodFromDatePicker(int index, String newDate, String tempName, String tempQuantity);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof DatePickerFragment.Callback) {
            callback = (DatePickerFragment.Callback) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        callback = null;
    }

    public DatePickerFragment() {
        // Required empty public constructor
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        foods = FoodItemRepository.getInstance();
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        int index = getArguments().getInt("index");
        String rawDate = "";
        if (++month < 9) {
            rawDate += "0";
        }
        rawDate += month + "/";
        if (day < 10) {
            rawDate += "0";
        }
        rawDate += day + "/" + year;
        if (index != -2) {
            try {
                foods.get(index).setExpirationDate(new DateTime(dateFormat.parse(rawDate)));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        callback.goToEditFoodFromDatePicker(index, rawDate, getArguments().getString("tempName"), getArguments().getString("tempQuantity"));
    }
}
