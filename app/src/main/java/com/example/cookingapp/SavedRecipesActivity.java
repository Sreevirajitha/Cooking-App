package com.example.cookingapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.cookingapp.Models.RecipeDetailsResponse;
import com.example.cookingapp.Listeners.RecipeDetailsListener;
import com.example.cookingapp.Listeners.RecipeClickListener;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SavedRecipesActivity extends AppCompatActivity {

    private RecyclerView recyclerViewSavedRecipes;
    private SavedRecipesAdapter savedRecipesAdapter;
    private Button clearAllButton;
    private SharedPreferences sharedPreferences;
    private RecipeClickListener recipeClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_recipes);

        recipeClickListener = new RecipeClickListener() {
            @Override
            public void onRecipeClicked(String id) {
                startActivity(new Intent(SavedRecipesActivity.this, RecipeActivity.class)
                        .putExtra("id", id));
            }
        };

        recyclerViewSavedRecipes = findViewById(R.id.recycler_saved_recipes);
        recyclerViewSavedRecipes.setLayoutManager(new LinearLayoutManager(this));

        clearAllButton = findViewById(R.id.button_clear_all);

        sharedPreferences = getSharedPreferences("SavedRecipes", MODE_PRIVATE);

        savedRecipesAdapter = new SavedRecipesAdapter(recipeClickListener);
        recyclerViewSavedRecipes.setAdapter(savedRecipesAdapter);

        clearAllButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearAllSavedRecipes();
            }
        });

        List<Integer> savedRecipeIds = getSavedRecipeIds();
        fetchSavedRecipesDetails(savedRecipeIds);
    }

    private List<Integer> getSavedRecipeIds() {
        List<Integer> savedRecipeIds = new ArrayList<>();

        Map<String, ?> savedRecipes = sharedPreferences.getAll();
        for (Map.Entry<String, ?> entry : savedRecipes.entrySet()) {
            if (entry.getValue() instanceof Boolean && (Boolean) entry.getValue()) {
                savedRecipeIds.add(Integer.parseInt(entry.getKey()));
            }
        }

        return savedRecipeIds;
    }

    private void fetchSavedRecipesDetails(List<Integer> savedRecipeIds) {
        RequestManager manager = new RequestManager(this);
        for (int id : savedRecipeIds) {
            manager.getRecipeDetails(recipeDetailsListener, id);
        }
    }

    private void clearAllSavedRecipes() {
        sharedPreferences.edit().clear().apply();
        savedRecipesAdapter.clearRecipes();
        Toast.makeText(this, "All saved recipes cleared", Toast.LENGTH_SHORT).show();
    }

    private final RecipeDetailsListener recipeDetailsListener = new RecipeDetailsListener() {
        @Override
        public void didFetch(RecipeDetailsResponse response, String message) {
            savedRecipesAdapter.addRecipe(response);
        }

        @Override
        public void didError(String message) {
            // Handle the error if fetching recipe details fails
        }
    };

    private class SavedRecipesAdapter extends RecyclerView.Adapter<SavedRecipesAdapter.ViewHolder> {

        private List<RecipeDetailsResponse> savedRecipes;
        private RecipeClickListener clickListener;

        public SavedRecipesAdapter(RecipeClickListener listener) {
            this.savedRecipes = new ArrayList<>();
            this.clickListener = listener;
        }

        public void addRecipe(RecipeDetailsResponse recipe) {
            savedRecipes.add(recipe);
            notifyDataSetChanged();
        }

        public void clearRecipes() {
            savedRecipes.clear();
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_saved_recipe, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            RecipeDetailsResponse recipe = savedRecipes.get(position);
            holder.bind(recipe);
        }

        @Override
        public int getItemCount() {
            return savedRecipes.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            private CardView cardView;
            private TextView textViewRecipeName;

            public ViewHolder(View itemView) {
                super(itemView);
                cardView = itemView.findViewById(R.id.cardView_recipe);
                textViewRecipeName = itemView.findViewById(R.id.textViewRecipeName);
            }

            public void bind(final RecipeDetailsResponse recipe) {
                String recipeName = recipe.title;
                textViewRecipeName.setText(recipeName);

                cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        clickListener.onRecipeClicked(String.valueOf(recipe.id));
                    }
                });
            }
        }
    }
}