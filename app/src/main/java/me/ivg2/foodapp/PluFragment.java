package me.ivg2.foodapp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 */
public class PluFragment extends Fragment {
    private Button goButton;
    private static final int[] buttonIDArray = {R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4, R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9};
    private Button[] buttonsArray = new Button[buttonIDArray.length];
    private Button deleteButton;
    private ProgressBar pluProgress;
    private String produce;
    private Callback callback;

    interface Callback {
        void goToManualFromPlu(String foodName);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof PluFragment.Callback) {
            callback = (PluFragment.Callback) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        callback = null;
    }

    public PluFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_plu, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        pluProgress = view.findViewById(R.id.pluProgress);
        final EditText userCode = view.findViewById(R.id.etPLU);
        userCode.setInputType(InputType.TYPE_NULL);
        for (int i = 0; i < buttonIDArray.length; i++) {
            final int b = i;
            buttonsArray[b] = view.findViewById(buttonIDArray[b]);
            buttonsArray[b].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    userCode.setText(userCode.getText().insert(userCode.getText().length(), Integer.toString(b)));
                }
            });
        }
        deleteButton = view.findViewById(R.id.btnDelete);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (userCode.getText().length() > 0) {
                    userCode.setText(userCode.getText().delete(userCode.getText().length() - 1, userCode.getText().length()));
                }
            }
        });
        goButton = view.findViewById(R.id.btnEnter);
        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (userCode.getText().length() < 4) {
                    Toast.makeText(getActivity(), "Please enter a valid PLU", Toast.LENGTH_SHORT).show();
                    return;
                }
                pluProgress.setVisibility(ProgressBar.VISIBLE);
                final int pluCode = Integer.parseInt(userCode.getText().toString());

                Random rand = new Random();
                int value = rand.nextInt(5);
                if (value == 1) {
                    //error
                    userCode.setText("");
                    Toast.makeText(getContext(), "Error during PLU look up, try again", Toast.LENGTH_LONG).show();
                    pluProgress.setVisibility(ProgressBar.INVISIBLE);
                } else {
                    int firstDigit = Integer.parseInt(Integer.toString(pluCode).substring(0, 1));
                    if (firstDigit == 1) {
                        callback.goToManualFromPlu("apple");
                    } else if (firstDigit == 2) {
                        callback.goToManualFromPlu("banana");
                    } else if (firstDigit == 3) {
                        callback.goToManualFromPlu("pear");
                    } else if (firstDigit == 4) {
                        callback.goToManualFromPlu("grapes");
                    } else if (firstDigit == 5) {
                        callback.goToManualFromPlu("peach");
                    } else {
                        callback.goToManualFromPlu("plum");
                    }
                }
            }
        });
    }
}
