package com.example.cookingapp.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cookingapp.Models.RecipeDetailsResponse;
import com.example.cookingapp.R;

import java.util.ArrayList;
import java.util.List;

public class SavedRecipesAdapter extends RecyclerView.Adapter<SavedRecipesAdapter.RecipeViewHolder> {

    private List<RecipeDetailsResponse> savedRecipes;

    public SavedRecipesAdapter() {
        this.savedRecipes = new ArrayList<>();
    }

    public void addRecipe(RecipeDetailsResponse recipe) {
        savedRecipes.add(recipe);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_saved_recipe, parent, false);
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        RecipeDetailsResponse recipe = savedRecipes.get(position);
        holder.bind(recipe);
    }

    @Override
    public int getItemCount() {
        return savedRecipes.size();
    }

    static class RecipeViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewRecipeName;

        public RecipeViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewRecipeName = itemView.findViewById(R.id.textViewRecipeName);
        }

        public void bind(RecipeDetailsResponse recipe) {
            textViewRecipeName.setText(recipe.title);
            // Add more bindings here if needed
        }
    }
}