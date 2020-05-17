package com.example.android.baker.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import com.example.android.baker.model.Ingredient;

import java.util.List;

@Dao
public interface IngredientDao {

    @Query("SELECT * FROM ingredient")
    List<Ingredient> loadIngredients();

    @Query("SELECT * FROM ingredient WHERE ingredient = :ingredientKey")
    Ingredient fetchIngredient(String ingredientKey);

    @Insert
    void insertIngredient(Ingredient ingredient);

}

