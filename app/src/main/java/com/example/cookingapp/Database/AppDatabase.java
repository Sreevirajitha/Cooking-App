package com.example.cookingapp.Database;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.example.cookingapp.Dao.RecipeDao;
import com.example.cookingapp.Entities.SavedRecipe;

@Database(entities = {SavedRecipe.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {
    public abstract RecipeDao recipeDao();
    private static volatile AppDatabase INSTANCE;

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, "recipe_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}