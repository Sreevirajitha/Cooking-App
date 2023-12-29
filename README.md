# Cooking App 🍳

The Cooking Application is a user-friendly mobile app that leverages the power of the Spoonacular API (https://spoonacular.com/food-api/console#Dashboard) to provide users with a delightful culinary experience. The app seamlessly integrates with the API to offer a range of features that enhance the user's cooking journey.

## Welcome to the Cooking App! 🍽️

Hungry for a culinary adventure? You're in the right place! Our Cooking App, powered by the Spoonacular API, is your kitchen companion, ready to make your cooking journey a whole lot tastier.

### Check Out the App in Action

Want to see how it works? Watch my youtube demo on [YouTube](https://www.youtube.com/watch?v=83ALOBbmoWk&ab_channel=lorenzalvin). Please note that in the video, you might see loaded recipes because of an active internet connection.

## What's Cooking?

Here's what our app brings to the table:

### Explore Recipes

Kick off your culinary exploration in the "MainActivity." It's like opening a surprise recipe box filled with all sorts of dishes, from main courses to mouthwatering desserts and refreshing beverages.

### Dive into Recipe Details

When you find a recipe that tickles your taste buds, hop into the "RecipeActivity." Here, you'll get the inside scoop on your chosen dish, including its name, source, and a tantalizing image. Plus, we've got the nitty-gritty details like ingredient lists to ensure you're ready to cook up a storm.

### Discover Similar Delights

As you're checking out a recipe in the "RecipeActivity," we'll suggest up to four similar recipes. It's like having a culinary assistant, always ready with exciting alternatives that match your preferences.

### Save Your Favorites

Got a recipe that deserves a spot in your personal cookbook? Simply hit the "Save Recipe" button. Your saved recipes will have a cozy home in the "Saved Recipes" section, making sure your culinary inspirations are never too far away.

### Easy Navigation

We've made it super easy to move around. The "Home" button in the "RecipeActivity" lets you jump back to the "MainActivity" in a snap, ensuring a seamless user experience.

### Tag Your Recipes

Customize your recipe hunt with our menu spinner. Pick from a variety of tags that sort recipes into categories like main courses, side dishes, desserts, appetizers, salads, and more. It's like having a personal food filter to discover dishes that match your cravings.


## Video Demo 📽️

Want to see the Cooking App in action? Check out our [video demo](https://www.youtube.com/watch?v=83ALOBbmoWk&ab_channel=lorenzalvin). Please note that the demo may show loaded recipes due to an active internet connection.

## How to Use 📱

Let's get cooking with the Cooking App:

1. **Clone the Repository**: Start by cloning this repository to your local machine:
   git clone https://github.com/YuhanPizza/Cooking-App.git

2. **Open the Project**: Fire up Android Studio and open the Cooking App project.

3. **Run the App**: Launch the app on an Android emulator or a physical device.

4. **Exploring the App**:
   - **MainActivity**: Begin your culinary journey here. Use the "Spinner Menu" to select recipe tags that interest you.
   - **RecipeActivity**: Dive into the details of a chosen recipe. Access information, ingredients, and similar recipe suggestions. Save recipes with the "Save Recipe" button.
   - **SavedRecipesActivity**: Access your curated collection of saved recipes in this dedicated section.

## Code Overview 🧰

Let's peek behind the scenes of the Cooking App:

- `MainActivity.java`: The main activity for recipe discovery and tag-based filtering.
- `RecipeActivity.java`: Handles the display of detailed recipe information and similar recipe suggestions.
- `SavedRecipesActivity.java`: Manages your saved recipe collection.
- `AppDatabase`: The app's database for storing saved recipes.
- `RecipeDao`: Defines the data access methods for the database.
- `Adapters`:
  - `IngredientsAdapter.java`: Manages ingredient lists in the UI.
  - `RandomRecipeAdapter.java`: Handles random recipe display.
  - `SavedRecipeAdapter.java`: Manages saved recipe display that is stored in local storage with RoomDB.
  - `SimilarRecipeAdapter.java`: Handles similar recipe suggestions.
- `Layouts`: The XML layout files that define the app's user interface.

## Spice Up Your Culinary Journey :fire:

In a nutshell, our Cooking App is here to spice up your cooking game. Whether you're a seasoned chef or just starting your culinary journey, our user-friendly interface, integration with the Spoonacular API, and innovative features are here to make your time in the kitchen a flavor-packed delight. Get ready to elevate your cooking experience with our app and discover a world of taste and inspiration!

Feel free to explore the code files to gain a deeper understanding of how the Cooking App works. Happy cooking! 🍽️
