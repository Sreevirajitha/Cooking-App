package com.example.cookingapp;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.cookingapp.Adapters.RandomRecipeAdapter;
import com.example.cookingapp.Listeners.RandomRecipeListener;
import com.example.cookingapp.Listeners.RecipeClickListener;
import com.example.cookingapp.Models.RandomRecipeRes;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ProgressDialog dialog;
    RequestManager manager;
    RandomRecipeAdapter randomRecipeAdapter;
    RecyclerView recyclerView;
    Spinner spinner;
    List<String> tags = new ArrayList<>();
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dialog = new ProgressDialog(this);
        dialog.setTitle("Loading...");

        searchView = findViewById(R.id.searchView_home);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() { //set for search function
            @Override
            public boolean onQueryTextSubmit(String query) {
                tags.clear();
                tags.add(query.toLowerCase());//change to lowercase so no matter how it is typed
                manager.getRandomRecipes(randomRecipeListener, tags);
                dialog.show();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        spinner = findViewById(R.id.spinner_tags); //initializing spinner
        ArrayAdapter arrayAdapter = ArrayAdapter.createFromResource( //creating an array adapter
                this,
                R.array.tags,
                R.layout.spinner_text
        );
        arrayAdapter.setDropDownViewResource(R.layout.spinner_inner_text);
        spinner.setAdapter(arrayAdapter); //attach the array adapter to spinner
        spinner.setOnItemSelectedListener(spinnerSelectedListener);//on set listener when spinner item is selected.

        manager = new RequestManager(this);
    }
    private final AdapterView.OnItemSelectedListener spinnerSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
            String selectedTag = adapterView.getSelectedItem().toString();
            if (selectedTag.equals("Saved Recipes")) {
                startActivity(new Intent(MainActivity.this, SavedRecipesActivity.class));
            } else {
                tags.clear();
                tags.add(selectedTag);
                manager.getRandomRecipes(randomRecipeListener, tags);
                dialog.show();
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };
    private final RandomRecipeListener randomRecipeListener = new RandomRecipeListener() {//function to load the menu
        @Override
        public void didFetch(@NonNull RandomRecipeRes response, String message) {
            recyclerView = findViewById(R.id.recycler_view);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this,1));
            randomRecipeAdapter = new RandomRecipeAdapter(MainActivity.this, response.recipes, recipeClickListener);
            recyclerView.setAdapter(randomRecipeAdapter);
        }

        @Override
        public void didError(String message) {
            Toast.makeText(MainActivity.this,message + "Error: randomRecipeListener", Toast.LENGTH_SHORT).show();
        }
    };

    private final RecipeClickListener recipeClickListener = new RecipeClickListener() {
        @Override
        public void onRecipeClicked(String id) {
            startActivity(new Intent(MainActivity.this, RecipeActivity.class) //creating new activity
                    .putExtra("id",id)); //passing the id done in assignment 2
        }
    };
    @Override
    protected void onResume() {
        super.onResume();
        // Reset the spinner to the default position
        if (spinner != null) {
            spinner.setSelection(0); // 0 is the index of the default position
        }
    }
}