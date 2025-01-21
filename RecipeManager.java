package CSC131;

import java.util.HashMap;
import java.util.Map;

public class RecipeManager {
    private HashMap<String, HashMap<String, Integer>> recipes;

    public RecipeManager() {
        recipes = new HashMap<>();
        loadInitialRecipes();
    }

    // Method to add a recipe
    public void addRecipe(String dishName, HashMap<String, Integer> ingredients) {
        recipes.put(dishName, ingredients);
    }

    // Method to retrieve all recipes
    public HashMap<String, HashMap<String, Integer>> getRecipes() {
        return recipes;
    }

    // Method to calculate available quantity of a dish
    public int calculateAvailableQuantity(String dishName, Inventory inventory) {
        if (!recipes.containsKey(dishName)) {
            return 0; // Recipe not found
        }

        HashMap<String, Integer> requiredIngredients = recipes.get(dishName);
        int minQuantity = Integer.MAX_VALUE;

        for (Map.Entry<String, Integer> entry : requiredIngredients.entrySet()) {
            String ingredient = entry.getKey();
            int requiredAmount = entry.getValue();
            int availableAmount = inventory.getIngredientQuantity(ingredient);

            if (availableAmount < requiredAmount) {
                return 0; // Not enough of this ingredient
            }

            int possibleDishes = availableAmount / requiredAmount;
            if (possibleDishes < minQuantity) {
                minQuantity = possibleDishes;
            }
        }

        return minQuantity;
    }

    // Method to load initial recipes
    private void loadInitialRecipes() {
        // Adding recipes for the specified dishes with different ingredients and varying availability
        HashMap<String, Integer> sushiIngredients = new HashMap<>();
        sushiIngredients.put("Rice", 1); // Done
        sushiIngredients.put("Fish", 2); // Done
        addRecipe("Sushi", sushiIngredients);

        // Add recipes for other specified dishes similarly
        // Sashimi
        HashMap<String, Integer> sashimiIngredients = new HashMap<>();
        sashimiIngredients.put("Fish", 2);  // Done
        addRecipe("Sashimi", sashimiIngredients);

        // Tempura
        HashMap<String, Integer> tempuraIngredients = new HashMap<>();
        tempuraIngredients.put("Fish", 1); // Done
        tempuraIngredients.put("Flour", 1); // Done
        addRecipe("Tempura", tempuraIngredients);

        // Ramen
        HashMap<String, Integer> ramenIngredients = new HashMap<>();
        ramenIngredients.put("Noodles", 1); // Dones
        ramenIngredients.put("Egg", 1); // Dones
        ramenIngredients.put("Pork", 1); // DOnes
        addRecipe("Ramen", ramenIngredients);

        // Udon
        HashMap<String, Integer> udonIngredients = new HashMap<>();
        udonIngredients.put("Noodles", 1); // Dones
        addRecipe("Udon", udonIngredients);

        // Takoyaki
        HashMap<String, Integer> takoyakiIngredients = new HashMap<>();
        takoyakiIngredients.put("Octopus", 1); // D
        takoyakiIngredients.put("Flour", 1); // D
        addRecipe("Takoyaki", takoyakiIngredients);

        // Miso Soup
        HashMap<String, Integer> misoSoupIngredients = new HashMap<>();
        misoSoupIngredients.put("Soy Sauce", 1); // D
        misoSoupIngredients.put("Tofu", 1); // D
        addRecipe("Miso Soup", misoSoupIngredients);

        // Yakitori
        HashMap<String, Integer> yakitoriIngredients = new HashMap<>();
        yakitoriIngredients.put("Chicken", 1);
        yakitoriIngredients.put("Soy Sauce", 1);
        addRecipe("Yakitori", yakitoriIngredients);

        // Onigiri
        HashMap<String, Integer> onigiriIngredients = new HashMap<>();
        onigiriIngredients.put("Rice", 1);
        onigiriIngredients.put("Seaweed", 1);
        addRecipe("Onigiri", onigiriIngredients);

        // Teriyaki Chicken
        HashMap<String, Integer> teriyakiChickenIngredients = new HashMap<>();
        teriyakiChickenIngredients.put("Chicken", 1);
        teriyakiChickenIngredients.put("Soy Sauce", 1);
        addRecipe("Teriyaki Chicken", teriyakiChickenIngredients);
    }
}
