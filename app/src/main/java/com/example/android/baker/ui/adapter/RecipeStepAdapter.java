package com.example.android.baker.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.android.baker.R;
import com.example.android.baker.model.RecipeStep;

import java.util.List;

public class RecipeStepAdapter extends RecyclerView.Adapter<RecipeStepAdapter.RecipeStepViewHolder> {

    Context context;
    private List<RecipeStep> recipeSteps;
    private final RecipeStepClickListener recipeStepClickListener;
    private int selectedPos = RecyclerView.NO_POSITION;

    public RecipeStepAdapter(RecipeStepClickListener recipeStepClickListener) {
        this.recipeStepClickListener = recipeStepClickListener;
    }

    @NonNull
    @Override
    public RecipeStepViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.recipe_step, parent, false);
        return new RecipeStepViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeStepViewHolder recipeStepViewHolder, int position) {
        RecipeStep recipeStep = recipeSteps.get(position);
        recipeStepViewHolder.itemView.setSelected(selectedPos == position);
        recipeStepViewHolder.bind(recipeStep);
    }

    @Override
    public int getItemCount() {
        return (recipeSteps != null) ? recipeSteps.size() : 0;
    }

    class RecipeStepViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final View view;
        final TextView textViewRecipeStepNo;
        final TextView textViewRecipeStepDescription;

        public RecipeStepViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView.findViewById(R.id.recipe_step);
            textViewRecipeStepNo = itemView.findViewById(R.id.tv_recipe_step_no);
            textViewRecipeStepDescription = itemView.findViewById(R.id.tv_recipe_step_description);
            itemView.setOnClickListener(this);
        }

        void bind(RecipeStep recipeStep) {
            if (recipeStep != null) {
                if (getAdapterPosition() == 0) {
                    textViewRecipeStepNo.setText(recipeStep.getShortDescription());
                } else {
                    textViewRecipeStepNo.setText("Step " + (getAdapterPosition()));
                    textViewRecipeStepDescription.setText(recipeStep.getShortDescription());
                }
            }
        }

        @Override
        public void onClick(View v) {
            // Reference https://stackoverflow.com/questions/27194044/how-to-properly-highlight-selected-item-on-recyclerview
            notifyItemChanged(selectedPos);
            selectedPos = getLayoutPosition();
            notifyItemChanged(selectedPos);
            recipeStepClickListener.onRecipeStepClick(selectedPos);
        }
    }

    public void setRecipeStepList(List<RecipeStep> recipeSteps) {
        this.recipeSteps = recipeSteps;
        notifyDataSetChanged();
    }

    public interface RecipeStepClickListener {
        void onRecipeStepClick(int position);
    }

}
