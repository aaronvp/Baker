package com.example.android.baker.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import com.example.android.baker.R;
import com.example.android.baker.model.Recipe;
import com.example.android.baker.ui.fragment.RecipeDetailFragment;
import com.example.android.baker.ui.fragment.RecipeStepFragment;

import java.util.Objects;

import static com.example.android.baker.util.ApplicationConstants.*;

public class RecipeDetailActivity extends AppCompatActivity implements RecipeDetailFragment.OnRecipeStepSelector,
        RecipeStepFragment.StepButtonClick {

    private boolean twoPane;
    private RecipeStepFragment recipeStepFragment;
    private Recipe recipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_recipe);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        } else {
            if (intent.hasExtra(INTENT_KEY_RECIPE)) {
                recipe = intent.getParcelableExtra(INTENT_KEY_RECIPE);
                if (recipe != null) {
                    RecipeDetailFragment recipeDetailFragment = (RecipeDetailFragment) getFragment(R.id.recipe_detail_fragment);
                    if (recipeDetailFragment != null) {
                        recipeDetailFragment.setRecipe(recipe);
                    }
                    recipeStepFragment = (RecipeStepFragment) getFragment(R.id.recipe_step_fragment);
                    if (recipeStepFragment != null) {
                        twoPane = true;
                        recipeStepFragment.setStepButtonClick(this);
                        recipeStepFragment.setRecipeList(recipe.getRecipeStepList());
                        recipeStepFragment.setCurrentRecipeStep(0);
                    }
                }
            }
        }
        Objects.requireNonNull(this.getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private Fragment getFragment(int fragmentId) {
        FragmentManager fragmentManager = this.getSupportFragmentManager();
        return fragmentManager.findFragmentById(fragmentId);
    }

    @Override
    public void onRecipeStepClick(int position) {
        if (twoPane) {
            recipeStepFragment.setCurrentRecipeStep(position);
        } else {
            Intent intent = new Intent(this, RecipeStepActivity.class);
            intent.putExtra(INTENT_KEY_RECIPE, recipe);
            intent.putExtra(INTENT_KEY_RECIPE_CURRENT_STEP, position);
            startActivity(intent);
        }
    }

    @Override
    public void onPrevClick(int currentPosition) {
        recipeStepFragment.setCurrentRecipeStep(currentPosition);
    }

    @Override
    public void onNextClick(int currentPosition) {
        recipeStepFragment.setCurrentRecipeStep(currentPosition);
    }
}

