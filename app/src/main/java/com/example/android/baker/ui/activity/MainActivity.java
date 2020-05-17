package com.example.android.baker.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.android.baker.R;
import com.example.android.baker.database.AppDatabase;
import com.example.android.baker.model.Ingredient;
import com.example.android.baker.model.Recipe;
import com.example.android.baker.service.RecipeService;
import com.example.android.baker.ui.adapter.RecipeAdapter;
import com.example.android.baker.util.AppExecutors;
import com.example.android.baker.util.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.ArrayList;
import java.util.List;

import static com.example.android.baker.util.ApplicationConstants.INTENT_KEY_RECIPE;

public class MainActivity extends AppCompatActivity implements RecipeAdapter.ListItemClickListener {

    private RecipeAdapter recipeAdapter;
    private List<Recipe> recipes;
    private Context context;
    private static Retrofit retrofit;
    private static final String KEY_LIFECYCLE_RECIPES = "Recipes";
    private AppDatabase recipeDatabase;
    public static final String RECIPE_DB_URL =
            "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = MainActivity.this;
        recipeDatabase = AppDatabase.getInstance(this);
        RecyclerView recipeRecyclerView = findViewById(R.id.rv_recipeList);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, getSpan());
        recipeRecyclerView.setLayoutManager(gridLayoutManager);
        recipeAdapter = new RecipeAdapter(this);
        recipeRecyclerView.setAdapter(recipeAdapter);

        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(KEY_LIFECYCLE_RECIPES)) {
                Log.i(KEY_LIFECYCLE_RECIPES, "OnSaveInstanceState - Loading Saved Recipes");
                recipes = savedInstanceState.getParcelableArrayList(KEY_LIFECYCLE_RECIPES);
                recipeAdapter.setRecipeData(recipes);
            }
        } else {
            getRecipes();
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i(KEY_LIFECYCLE_RECIPES, "OnSaveInstanceState - Saving Recipes");
        outState.putParcelableArrayList(KEY_LIFECYCLE_RECIPES, new ArrayList<Parcelable>(recipes));
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {
        Recipe recipe = recipes.get(clickedItemIndex);
        Intent intent = new Intent(context, RecipeDetailActivity.class);
        intent.putExtra(INTENT_KEY_RECIPE, recipe);
        startActivity(intent);
    }

    private void getRecipes() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(RECIPE_DB_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        RecipeService recipeService = retrofit.create(RecipeService.class);
        Call<List<Recipe>> call = recipeService.getRecipeList();
        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(@NonNull Call<List<Recipe>> call, @NonNull Response<List<Recipe>> response) {
                recipes = response.body();
                recipeAdapter.setRecipeData(recipes);
                // Save the first Recipe to display in the widget
                saveRecipe(recipes.get(0));
            }

            @Override
            public void onFailure(@NonNull Call<List<Recipe>> call, @NonNull Throwable t) {
                Log.e("TAG", "RetroFit " + t.getMessage());
            }
        });
    }

    private int getSpan() {
        float scaledWidth = Util.getScaledWidth(getApplicationContext());
        if (scaledWidth > 600 && scaledWidth <= 900) {
            return 2;
        } else if (scaledWidth > 900) {
            return 3;
        } else return 1;
    }


    public void saveRecipe(final Recipe recipe) {
        AppExecutors.getInstance().diskIO().execute(() -> {
            if (recipeDatabase.recipeDao().getRecipe() == null) {
                recipeDatabase.recipeDao().insertRecipe(recipe);
            }
            for (Ingredient ingredient : recipe.getIngredientList()) {
                if (recipeDatabase.ingredientDao().fetchIngredient(ingredient.getIngredient()) == null) {
                    recipeDatabase.ingredientDao().insertIngredient(ingredient);
                }
            }
        });
    }

}

