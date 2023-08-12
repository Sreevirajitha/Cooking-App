package com.example.cookingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cookingapp.Adapters.IngredientsAdapter;
import com.example.cookingapp.Adapters.SimilarRecipeAdapter;
import com.example.cookingapp.Database.AppDatabase;
import com.example.cookingapp.Entities.SavedRecipe;
import com.example.cookingapp.Listeners.RecipeClickListener;
import com.example.cookingapp.Listeners.RecipeDetailsListener;
import com.example.cookingapp.Listeners.SimilarRecipesListener;
import com.example.cookingapp.Models.RecipeDetailsResponse;
import com.example.cookingapp.Models.SimilarRecipeResponse;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecipeActivity extends AppCompatActivity {
    int id;
    TextView textView_meal_name;
    TextView textView_meal_source;
    TextView textView_meal_summary;
    ImageView imageView_meal_image;
    RecyclerView recycler_meal_ingredients;
    RecyclerView recycler_meal_similar;
    RequestManager manager;
    ProgressDialog dialog;
    IngredientsAdapter ingredientsAdapter; //adapter
    SimilarRecipeAdapter similarRecipeAdapter; //adapter
    AppDatabase appDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        appDatabase = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "app_database").build(); // Added

        findViews();

        id = Integer.parseInt(getIntent().getStringExtra("id"));//capture id from intent
        manager = new RequestManager(this);
        manager.getRecipeDetails(recipeDetailsListener, id);
        manager.getSimilarRecipes(similarRecipesListener, id);
        dialog = new ProgressDialog(this);
        dialog.setTitle("Loading Detail Information...");
        dialog.show();

        Button buttonSaveRecipe = findViewById(R.id.button_save_recipe);
        buttonSaveRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                manager.getRecipeDetails(new RecipeDetailsListener() { // Fetching the recipe details again to save
                    @Override
                    public void didFetch(RecipeDetailsResponse response, String message) {
                        saveRecipeLocally(response);
                    }

                    @Override
                    public void didError(String message) {
                        // Handle error here
                    }
                }, id);
            }
        });

        Button buttonHome = findViewById(R.id.button_home);
        buttonHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Bring the existing MainActivity to the foreground
                Intent homeIntent = new Intent(RecipeActivity.this, MainActivity.class);
                homeIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(homeIntent);
            }
        });
    }
    private void saveRecipeLocally(RecipeDetailsResponse recipeDetails) {
        SavedRecipe savedRecipe = new SavedRecipe(recipeDetails.id, recipeDetails.title, recipeDetails.summary, recipeDetails.image, recipeDetails.imageType);
        new Thread(() -> {
            appDatabase.recipeDao().insertRecipe(savedRecipe);
        }).start();

        Toast.makeText(this,  savedRecipe.image + "Recipe saved!", Toast.LENGTH_SHORT).show();
    }
    private void findViews() {
        textView_meal_name = findViewById(R.id.textView_meal_name);
        textView_meal_source = findViewById(R.id.textView_meal_source);
        textView_meal_summary = findViewById(R.id.textView_meal_summary);
        imageView_meal_image = findViewById(R.id.imageView_meal_image);
        recycler_meal_ingredients = findViewById(R.id.recycler_meal_ingredients);
        recycler_meal_similar = findViewById(R.id.recycler_meal_similar);
    }

    private final RecipeDetailsListener recipeDetailsListener = new RecipeDetailsListener() {
        @Override
        public void didFetch(RecipeDetailsResponse response, String message) { //loading information to be displayed in activity_recipe
            dialog.dismiss();
            textView_meal_name.setText(response.title); //title
            textView_meal_source.setText(response.sourceName);
            String plainTextSummary = response.summary.replaceAll("<[^>]+>", ""); //removes html tags with regex
            textView_meal_summary.setText(plainTextSummary); //display summary without HTML tags
            Picasso.get().load(response.image).into(imageView_meal_image); //dish image

            //recycler_meal_ingredients.setHasFixedSize(true);
            recycler_meal_ingredients.setLayoutManager(new LinearLayoutManager(RecipeActivity.this, LinearLayoutManager.VERTICAL,false));
            ingredientsAdapter = new IngredientsAdapter(RecipeActivity.this, response.extendedIngredients); //adapter object initialized
            recycler_meal_ingredients.setAdapter(ingredientsAdapter); //attach ingredients adapter to recycler view
        }

        @Override
        public void didError(String message) {
            Toast.makeText(RecipeActivity.this,message+"error in recipe details listener",Toast.LENGTH_SHORT).show();
        }
    };

    private final SimilarRecipesListener similarRecipesListener = new SimilarRecipesListener() {
        @Override
        public void didFetch(List<SimilarRecipeResponse> response, String message) {
            recycler_meal_similar.setHasFixedSize(true);
            recycler_meal_similar.setLayoutManager(new LinearLayoutManager(RecipeActivity.this, LinearLayoutManager.HORIZONTAL, false));
            similarRecipeAdapter = new SimilarRecipeAdapter(RecipeActivity.this, response,recipeClickListener );
            recycler_meal_similar.setAdapter(similarRecipeAdapter);
        }

        @Override
        public void didError(String message) {
            Toast.makeText(RecipeActivity.this,message +"Error in Similar Recipes Listener",Toast.LENGTH_SHORT).show();
        }
    };

    private final RecipeClickListener recipeClickListener = new RecipeClickListener() {
        @Override
        public void onRecipeClicked(String id) {
            startActivity(new Intent(RecipeActivity.this, RecipeActivity.class) //creating new activity
                    .putExtra("id",id)); //passing the id done in assignment 2

        }
    };
}