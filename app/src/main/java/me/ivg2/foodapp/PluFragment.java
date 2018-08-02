package me.ivg2.foodapp;

import android.content.Context;
import android.os.AsyncTask;
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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.ivg2.foodapp.server.UrlManager;

/**
 * A simple {@link Fragment} subclass.
 */
public class PluFragment extends Fragment {
    @BindView(R.id.btnEnter)
    Button goButton;
    @BindView(R.id.btnDelete)
    Button deleteButton;
    @BindView(R.id.pluProgress)
    ProgressBar pluProgress;
    private static final int[] buttonIDArray = {R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4, R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9};
    private Button[] buttonsArray = new Button[buttonIDArray.length];
    private String produce;
    private Unbinder unbinder;
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
        unbinder = ButterKnife.bind(this, view);
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
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (userCode.getText().length() > 0) {
                    userCode.setText(userCode.getText().delete(userCode.getText().length() - 1, userCode.getText().length()));
                }
            }
        });
        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (userCode.getText().length() < 4) {
                    Toast.makeText(getActivity(), "Please enter a valid PLU", Toast.LENGTH_SHORT).show();
                    return;
                }
                pluProgress.setVisibility(ProgressBar.VISIBLE);
                final int pluCode = Integer.parseInt(userCode.getText().toString());

                userCode.setText("");
                GetPluTask task = (GetPluTask) new GetPluTask(pluCode).execute();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    class GetPluTask extends AsyncTask {
        private int pluCode;

        public GetPluTask(int plu) {
            super();
            pluCode = plu;
        }

        @Override
        protected String doInBackground(Object[] objects) {
            String foodName = null;
            URL url = null;

            try {
                url = UrlManager.getPluEndpoint(pluCode);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                InputStream in = connection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                foodName = reader.readLine();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (foodName == null) {
                Toast.makeText(getContext(), "Error during PLU look up, try again", Toast.LENGTH_LONG).show();
                pluProgress.setVisibility(ProgressBar.INVISIBLE);
            } else {
                callback.goToManualFromPlu(foodName);
            }
            return null;
        }

    }
}
