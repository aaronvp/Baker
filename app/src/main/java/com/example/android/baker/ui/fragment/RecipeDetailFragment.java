package com.example.android.baker.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.android.baker.R;
import com.example.android.baker.model.Ingredient;
import com.example.android.baker.model.Recipe;
import com.example.android.baker.ui.adapter.RecipeStepAdapter;

import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecipeDetailFragment extends Fragment implements RecipeStepAdapter.RecipeStepClickListener {

    public RecipeStepAdapter recipeStepAdapter;
    TextView textViewIngredients;
    OnRecipeStepSelector recipeStepSelector;

    public interface OnRecipeStepSelector {
        void onRecipeStepClick(int position);
    }

    // Override onAttach to make sure that the container activity has implemented the callback
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        // This makes sure that the host activity has implemented the callback interface
        // If not, it throws an exception
        try {
            recipeStepSelector = (OnRecipeStepSelector) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnRecipeStepSelector");
        }
    }

    public RecipeDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_recipe_detail, container, false);
        textViewIngredients = rootView.findViewById(R.id.tv_ingredients_list);
        RecyclerView recipeRecyclerView = rootView.findViewById(R.id.rv_recipeSteps);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recipeRecyclerView.setLayoutManager(layoutManager);
        recipeRecyclerView.addItemDecoration(
                new DividerItemDecoration(Objects.requireNonNull(getContext()), DividerItemDecoration.VERTICAL));
        recipeStepAdapter = new RecipeStepAdapter(this);
        recipeRecyclerView.setAdapter(recipeStepAdapter);
        return rootView;
    }

    public void setRecipe(Recipe recipe) {
        int i = 1;
        List<Ingredient> ingredients = recipe.getIngredientList();
        for (Ingredient ingredient : ingredients) {
            textViewIngredients.append(formatIngredient(ingredient, i++));
        }
        recipeStepAdapter.setRecipeStepList(recipe.getRecipeStepList());
    }

    public static String formatIngredient(Ingredient ingredient, int i) {
        String blank = " ";
        String lineBreak = "\n";
        return i + "." + blank +
                ingredient.getQuantity() + blank +
                ingredient.getMeasure() + blank +
                ingredient.getIngredient() +
                lineBreak;
    }

    @Override
    public void onRecipeStepClick(int position) {
        Log.i("Recipes", "RecipeDetailFragment clicked position" + position);
        recipeStepSelector.onRecipeStepClick(position);
    }

}
