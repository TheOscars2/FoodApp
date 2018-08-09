package me.ivg2.foodapp;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class InstructionsAdapter extends RecyclerView.Adapter<InstructionsAdapter.ViewHolder> {
    private ArrayList<String> instructions;
    Context context;


    public InstructionsAdapter(ArrayList<String> instructions) {
        this.instructions = instructions;
    }

    @NonNull
    @Override
    public InstructionsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View recipeView = inflater.inflate(R.layout.simple_text, viewGroup, false);
        InstructionsAdapter.ViewHolder view = new InstructionsAdapter.ViewHolder(recipeView);
        return view;
    }

    @Override
    public void onBindViewHolder(@NonNull final InstructionsAdapter.ViewHolder holder, final int index) {
        String instruction = instructions.get(index);
        holder.tvInstruction.setText(instruction);
    }

    @Override
    public int getItemCount() {
        return instructions.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        @BindView(R.id.simple_text)
        TextView tvInstruction;
        ImageView ivBackground;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            ivBackground = itemView.findViewById(R.id.background);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            tvInstruction.setTypeface(null, Typeface.BOLD);
        }
    }
}
