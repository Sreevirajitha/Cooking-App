package com.example.cookingapp;

import android.content.Context;

import com.example.cookingapp.Listeners.RandomRecipeListener;
import com.example.cookingapp.Models.RandomRecipeRes;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class RequestManager {
    Context context;
    Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.spoonacular.com/").addConverterFactory(GsonConverterFactory.create()).build();


    public RequestManager(Context context) {
        this.context = context;
    }
    public void getRandomRecipes(RandomRecipeListener listener, List<String> tags){
        RandomRecipe randomRecipe = retrofit.create(RandomRecipe.class);
        Call<RandomRecipeRes> call = randomRecipe.RandomRecipe(context.getString(R.string.api_key), "10", tags);
        call.enqueue(new Callback<RandomRecipeRes>() {
            @Override
            public void onResponse(Call<RandomRecipeRes> call, Response<RandomRecipeRes> response) {
               if(!response.isSuccessful()){listener.didError(response.message()); return;}
               listener.didFetch(response.body(), response.message());
            }

            @Override
            public void onFailure(Call<RandomRecipeRes> call, Throwable t) {
                listener.didError(t.getMessage());
            }
        });
    }
    private interface RandomRecipe{
        @GET("recipes/random")
        Call<RandomRecipeRes> RandomRecipe(
                @Query("apiKey") String apiKey,
                @Query("number") String number,
                @Query("tags") List<String> tags
        );
    }
}
