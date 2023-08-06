package com.example.cookingapp.Listeners;

import com.example.cookingapp.Models.RandomRecipeRes;

public interface RandomRecipeListener {
    void didFetch(RandomRecipeRes response, String message);
    void didError(String message);
}
