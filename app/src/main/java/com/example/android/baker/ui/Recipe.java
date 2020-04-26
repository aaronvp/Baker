package com.example.android.baker.ui;

import java.util.ArrayList;
import java.util.List;

public class Recipe {

    private String recipeName;

    public Recipe(String recipeName) {
        this.recipeName = recipeName;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public static List<Recipe> getRecipeList() {
        Recipe r1 = new Recipe("Recipe 1");
        Recipe r2 = new Recipe("Recipe 2");
        Recipe r3 = new Recipe("Recipe 3");
        List<Recipe> recipeList = new ArrayList<>();
        recipeList.add(r1);
        recipeList.add(r2);
        recipeList.add(r3);
        return  recipeList;
    }

}

