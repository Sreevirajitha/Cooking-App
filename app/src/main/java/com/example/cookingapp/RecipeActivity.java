package com.example.cookingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cookingapp.Adapters.IngredientsAdapter;
import com.example.cookingapp.Listeners.RecipeDetailsListener;
import com.example.cookingapp.Models.RecipeDetailsResponse;
import com.squareup.picasso.Picasso;

public class RecipeActivity extends AppCompatActivity {
    int id;
    TextView textView_meal_name;
    TextView textView_meal_source;
    TextView textView_meal_summary;
    ImageView imageView_meal_image;
    RecyclerView recycler_meal_ingredients;
    RequestManager manager;
    ProgressDialog dialog;
    IngredientsAdapter ingredientsAdapter; //adapter

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        findViews();

        id = Integer.parseInt(getIntent().getStringExtra("id"));//capture id from intent
        manager = new RequestManager(this);
        manager.getRecipeDetails(recipeDetailsListener, id);
        dialog = new ProgressDialog(this);
        dialog.setTitle("Loading Detail Information...");
        dialog.show();
    }

    private void findViews() {
        textView_meal_name = findViewById(R.id.textView_meal_name);
        textView_meal_source = findViewById(R.id.textView_meal_source);
        textView_meal_summary = findViewById(R.id.textView_meal_summary);
        imageView_meal_image = findViewById(R.id.imageView_meal_image);
        recycler_meal_ingredients = findViewById(R.id.recycler_meal_ingredients);
    }

    private final RecipeDetailsListener recipeDetailsListener = new RecipeDetailsListener() {
        @Override
        public void didFetch(RecipeDetailsResponse response, String message) { //loading information to be displayed in activity_recipe
            dialog.dismiss();
            textView_meal_name.setText(response.title); //title
            textView_meal_source.setText(response.sourceName);
            textView_meal_summary.setText(response.summary); //summary
            Picasso.get().load(response.image).into(imageView_meal_image); //dish image

            recycler_meal_ingredients.setHasFixedSize(true);
            recycler_meal_ingredients.setLayoutManager(new LinearLayoutManager(RecipeActivity.this, LinearLayoutManager.HORIZONTAL,false));
            ingredientsAdapter = new IngredientsAdapter(RecipeActivity.this, response.extendedIngredients); //adapter object initialized
            recycler_meal_ingredients.setAdapter(ingredientsAdapter); //attach ingredients adapter to recycler view
        }

        @Override
        public void didError(String message) {
            Toast.makeText(RecipeActivity.this,message,Toast.LENGTH_SHORT).show();
        }
    };
}