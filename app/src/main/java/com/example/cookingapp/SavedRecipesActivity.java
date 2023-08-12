package com.example.cookingapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.cookingapp.Adapters.SavedRecipesAdapter;
import com.example.cookingapp.Database.AppDatabase;
import com.example.cookingapp.Entities.SavedRecipe;
import com.example.cookingapp.Listeners.RecipeClickListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import android.os.AsyncTask;

public class SavedRecipesActivity extends AppCompatActivity {

    private RecyclerView recyclerViewSavedRecipes;
    private SavedRecipesAdapter savedRecipesAdapter;
    private Button clearAllButton;
    private AppDatabase appDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_recipes);

        appDatabase = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "app_database").build();

        recyclerViewSavedRecipes = findViewById(R.id.recycler_saved_recipes);
        recyclerViewSavedRecipes.setLayoutManager(new LinearLayoutManager(this));

        clearAllButton = findViewById(R.id.button_clear_all);

        savedRecipesAdapter = new SavedRecipesAdapter(new SavedRecipesAdapter.RecipeClickListener() {
            @Override
            public void onRecipeClicked(String id) {
                startActivity(new Intent(SavedRecipesActivity.this, RecipeActivity.class).putExtra("id", id));
            }
        });
        recyclerViewSavedRecipes.setAdapter(savedRecipesAdapter);

        clearAllButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearAllSavedRecipes();
            }
        });

        Button buttonHome = findViewById(R.id.button_home_saved);
        buttonHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homeIntent = new Intent(SavedRecipesActivity.this, MainActivity.class);
                homeIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(homeIntent);
            }
        });

        // Use AsyncTask to retrieve saved recipes on a background thread
        new LoadSavedRecipesTask().execute();
    }



    private void clearAllSavedRecipes() {
        new ClearSavedRecipesTask().execute();
    }

    private class LoadSavedRecipesTask extends AsyncTask<Void, Void, List<SavedRecipe>> {
        @Override
        protected List<SavedRecipe> doInBackground(Void... voids) {
            return appDatabase.recipeDao().getAllSavedRecipes();
        }

        @Override
        protected void onPostExecute(List<SavedRecipe> savedRecipes) {
            savedRecipesAdapter.setSavedRecipes(savedRecipes);
        }
    }

    private class ClearSavedRecipesTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            appDatabase.recipeDao().clearAll();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            savedRecipesAdapter.clearRecipes();
            Toast.makeText(SavedRecipesActivity.this, "All saved recipes cleared", Toast.LENGTH_SHORT).show();
        }
    }
}