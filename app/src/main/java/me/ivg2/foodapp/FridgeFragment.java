package me.ivg2.foodapp;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import me.ivg2.foodapp.Model.Food;

/**
 * A simple {@link Fragment} subclass.
 */
public class FridgeFragment extends Fragment {
    private Button foodDetailBtn;
    private static Callback callback;

    FoodItemRepository foods;
    private FridgeGridAdapter gridAdapter;
    private RecyclerView rvPosts;

    interface Callback {
        void goToFoodDetail(Food food);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof FridgeFragment.Callback) {
            callback = (Callback) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();

        callback = null;
    }


    public FridgeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fridge, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        rvPosts= (RecyclerView) view.findViewById(R.id.rvGrid);
        foods = FoodItemRepository.getInstance();

        gridAdapter = new FridgeGridAdapter(foods);

        //RecyclerView setup -- layout manager and use adapter
        rvPosts.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        rvPosts.setAdapter(gridAdapter);
    }

    public static void onFoodViewClicked(Food food) {
        callback.goToFoodDetail(food);
    }
}
