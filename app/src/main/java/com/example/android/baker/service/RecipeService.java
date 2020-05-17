package com.example.android.baker.service;

import com.example.android.baker.model.Recipe;
import retrofit2.Call;
import retrofit2.http.GET;

import java.util.List;

public interface RecipeService {

    @GET("baking.json")
    Call<List<Recipe>> getRecipeList();

}
