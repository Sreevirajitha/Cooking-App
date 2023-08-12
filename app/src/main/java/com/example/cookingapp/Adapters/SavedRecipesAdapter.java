package com.example.cookingapp.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cookingapp.Entities.SavedRecipe;
import com.example.cookingapp.Listeners.RecipeClickListener;
import com.example.cookingapp.Models.RecipeDetailsResponse;
import com.example.cookingapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class SavedRecipesAdapter extends RecyclerView.Adapter<SavedRecipesAdapter.RecipeViewHolder> {

    private List<SavedRecipe> savedRecipes;
    private RecipeClickListener clickListener;

    public interface RecipeClickListener {
        void onRecipeClicked(String id);
    }

    public SavedRecipesAdapter(RecipeClickListener clickListener) {
        this.savedRecipes = new ArrayList<>();
        this.clickListener = clickListener;
    }

    public void setSavedRecipes(List<SavedRecipe> recipes) {
        savedRecipes = recipes;
        notifyDataSetChanged();
    }

    public void clearRecipes() {
        savedRecipes.clear();
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
        final SavedRecipe recipe = savedRecipes.get(position);
        holder.textViewRecipeName.setText(recipe.getName());
        Picasso.get().load(recipe.image).into(holder.imageViewRecipe);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onRecipeClicked(String.valueOf(recipe.getId()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return savedRecipes.size();
    }

    static class RecipeViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewRecipeName;
        private ImageView imageViewRecipe;

        public RecipeViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewRecipeName = itemView.findViewById(R.id.textViewRecipeName);
            imageViewRecipe = itemView.findViewById(R.id.imageViewRecipe); // Replace with the actual ID of your ImageView
        }
    }
}