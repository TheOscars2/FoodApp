
package me.ivg2.foodapp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class FridgeFragment extends Fragment {
    private static Callback callback;
    static FoodItemRepository foods;
    private static FridgeGridAdapter gridAdapter;
    @BindView(R.id.rvGrid)
    RecyclerView rvFoods;
    private SearchView fridgeSearchBar;
    private Unbinder unbinder;

    interface Callback {
        void goToFoodDetail(int index);

        void goToEditFood(int index);
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
        return inflater.inflate(R.layout.fragment_fridge, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("My Fridge");
        unbinder = ButterKnife.bind(this, view);
        foods = FoodItemRepository.getInstance();
        fridgeSearchBar = view.findViewById(R.id.searchBarFridge);
        gridAdapter = new FridgeGridAdapter(foods);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 3);
        rvFoods.setLayoutManager(gridLayoutManager);
        rvFoods.setAdapter(gridAdapter);
        fridgeSearchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                gridAdapter.filter(newText);
                return true;
            }
        });
    }

    public static void onFoodViewClicked(int index) {
        callback.goToFoodDetail(foods.getAll().indexOf(gridAdapter.getListOfFoodObjects().get(index)));
    }

    public static void onEditFoodClicked(int index) {
        callback.goToEditFood(foods.getAll().indexOf(gridAdapter.getListOfFoodObjects().get(index)));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}