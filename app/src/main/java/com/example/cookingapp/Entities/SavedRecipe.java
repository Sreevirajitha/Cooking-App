package com.example.cookingapp.Entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "saved_recipes")
public class SavedRecipe { // Renamed from Recipe
    @PrimaryKey
    public int id;
    public String title;
    public String description;
    public String image;
    public String imageType;

    // Add other fields if needed

    public SavedRecipe(int id, String title, String description, String image, String imageType) { // Updated constructor
        this.id = id;
        this.title = title;
        this.description = description;
        this.image = image;
        this.imageType = imageType;
    }

    public String getName() {
        return this.title;
    }

    public String getId() {
        return String.valueOf(this.id);
    }
}