package me.ivg2.foodapp;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
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
    private ProgressBar pluProgress;
    private String produce;
    private String userInput;

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
        goButton = view.findViewById(R.id.btnPLU);
        final EditText userCode = view.findViewById(R.id.etPLU);
        pluProgress = view.findViewById(R.id.pluProgress);
        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager inputManager = (InputMethodManager) getContext().getSystemService(getActivity().INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
                userInput = userCode.getText().toString();
                if (userInput.equals("")) {
                    Toast.makeText(getActivity(), "please enter PLU", Toast.LENGTH_LONG).show();
                    return;
                }
                pluProgress.setVisibility(ProgressBar.VISIBLE);

                //int pluCode = Integer.parseInt(userCode.getText().toString());
                //lookup in plu file what the pluCode is a key to
                //produce = whatever value was found
                //bundle the string

                Handler handler = new Handler();
                Runnable r = new Runnable() {
                    public void run() {
                        Random rand = new Random();
                        int value = rand.nextInt(5);
                        if (value == 1) {
                            //error
                            userCode.setText("");
                            Toast.makeText(getContext(), "Error during PLU look up, try again", Toast.LENGTH_LONG).show();
                            pluProgress.setVisibility(ProgressBar.INVISIBLE);
                        } else {
                            //apple
                            produce = "apple";
                            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                            userCode.setText("");
                            transaction.replace(R.id.homeFragment, ManualAddFragment.newInstance(produce));
                            transaction.commit();
                        }
                    }
                };
                handler.postDelayed(r, 3000);
            }
        });
    }
}
