# Cooking App 🍳

Welcome to the Cooking App! This Android application is your culinary companion, designed to elevate your cooking experience and tantalize your taste buds.

## Features 🍽️

- **Recipe Discovery**: Explore a wide range of randomly selected recipes, from appetizers to desserts.
- **Detailed Recipe Info**: Dive deep into each recipe with comprehensive details, including ingredients.
- **Discover Similar Recipes**: Get suggestions for similar recipes to expand your culinary horizons.
- **Save Your Favorites**: Create your personal culinary library by saving recipes.
- **Easy Navigation**: Seamlessly move between activities for a smooth user experience.
- **Recipe Tagging**: Customize your recipe search with tags for easy exploration.

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
  - `SavedRecipeAdapter.java`: Manages saved recipe display.
  - `SimilarRecipeAdapter.java`: Handles similar recipe suggestions.
- `Layouts`: The XML layout files that define the app's user interface.

Feel free to explore the code files to gain a deeper understanding of how the Cooking App works. Happy cooking! 🍽️
