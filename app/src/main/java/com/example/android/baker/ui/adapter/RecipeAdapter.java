package com.example.android.baker.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.android.baker.R;
import com.example.android.baker.model.Recipe;

import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {

    Context context;
    private List<Recipe> recipeList;

    private final ListItemClickListener listItemClickListener;

    public RecipeAdapter(ListItemClickListener listItemClickListener) {
        this.listItemClickListener = listItemClickListener;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.item_recipe, parent, false);
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder movieViewHolder, int position) {
        Recipe recipe = recipeList.get(position);
        movieViewHolder.bind(recipe);
    }

    @Override
    public int getItemCount() {
        return (recipeList != null) ? recipeList.size() : 0;
    }

    /**
     * Caching of the children views for a RecipeStep item
     */
    class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final View view;
        final TextView tvRecipeName;
        final TextView tvRecipeServings;
        final ImageView ivRecipeImage;

        public RecipeViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView.findViewById(R.layout.item_recipe);
            tvRecipeName = itemView.findViewById(R.id.tv_recipe_name);
            tvRecipeServings = itemView.findViewById(R.id.tv_recipe_servings);
            ivRecipeImage = itemView.findViewById(R.id.iv_recipe);
            itemView.setOnClickListener(this);
        }

        void bind(Recipe recipe) {
            tvRecipeName.setText(recipe.getName());
            tvRecipeServings.setText(getServings(recipe));
            Glide.with(this.itemView)
                    .load(recipe.getImage())
                    .placeholder(R.drawable.ic_cake_100dp)
                    .into(ivRecipeImage);
        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            listItemClickListener.onListItemClick(clickedPosition);
        }
    }

    public void setRecipeData(List<Recipe> recipeList) {
        this.recipeList = recipeList;
        notifyDataSetChanged();
    }

    public interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }

    private String getServings(Recipe recipe) {
        return context.getString(R.string.recipe_servings).concat(" ").concat(recipe.getServings());
    }

}

