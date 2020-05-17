package com.example.android.baker.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import com.example.android.baker.R;
import com.example.android.baker.model.Recipe;
import com.example.android.baker.ui.fragment.RecipeStepFragment;

import java.util.Objects;

import static com.example.android.baker.util.ApplicationConstants.INTENT_KEY_RECIPE;
import static com.example.android.baker.util.ApplicationConstants.INTENT_KEY_RECIPE_CURRENT_STEP;

public class RecipeStepActivity extends AppCompatActivity implements RecipeStepFragment.StepButtonClick{

    RecipeStepFragment recipeStepFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_step);
        Intent intent = getIntent();
        if (intent.hasExtra(INTENT_KEY_RECIPE)) {
            Recipe recipe = intent.getParcelableExtra(INTENT_KEY_RECIPE);
            if (intent.hasExtra(INTENT_KEY_RECIPE_CURRENT_STEP)) {
                int recipeStepPosition = intent.getIntExtra(INTENT_KEY_RECIPE_CURRENT_STEP, 0);
                this.recipeStepFragment =
                        (RecipeStepFragment) getFragment(R.id.recipe_step_fragment);
                recipeStepFragment.setStepButtonClick(this);
                recipeStepFragment.setRecipeList(Objects.requireNonNull(recipe).getRecipeStepList());
                recipeStepFragment.setCurrentRecipeStep(recipeStepPosition);
            }
        }
    }

    private Fragment getFragment(int fragmentId) {
        FragmentManager fragmentManager = this.getSupportFragmentManager();
        return fragmentManager.findFragmentById(fragmentId);
    }

    @Override
    public void onPrevClick(int currentPosition) {
        Log.i("Baker", "RecipeStepActivity - new Step " + currentPosition);
        this.recipeStepFragment.setCurrentRecipeStep(currentPosition);
    }

    @Override
    public void onNextClick(int currentPosition) {
        Log.i("Baker", "RecipeStepActivity - new Step " + currentPosition);
        this.recipeStepFragment.setCurrentRecipeStep(currentPosition);
    }
}
