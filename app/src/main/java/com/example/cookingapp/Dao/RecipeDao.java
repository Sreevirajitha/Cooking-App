package com.example.cookingapp.Dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import com.example.cookingapp.Entities.SavedRecipe;
import java.util.List;

@Dao
public interface RecipeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertRecipe(SavedRecipe recipe);

    @Query("SELECT * FROM saved_recipes")
    List<SavedRecipe> getAllSavedRecipes();

    @Query("DELETE FROM saved_recipes")
    void clearAll();
}