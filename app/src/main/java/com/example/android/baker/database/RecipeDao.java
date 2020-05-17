package com.example.android.baker.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import com.example.android.baker.model.Recipe;

@Dao
public interface RecipeDao {

    @Query("SELECT * FROM recipe ORDER BY ROWID ASC LIMIT 1")
    Recipe getRecipe();

    @Insert
    void insertRecipe(Recipe recipe);

}

