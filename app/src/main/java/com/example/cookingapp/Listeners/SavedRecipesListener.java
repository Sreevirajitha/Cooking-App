package com.example.cookingapp.Listeners;

import com.example.cookingapp.Models.RecipeDetailsResponse;

public interface SavedRecipesListener {
    void onRecipeLoaded(RecipeDetailsResponse savedRecipe);
    void onError(String errorMessage);
}