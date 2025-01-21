package CSC131;

import java.util.HashMap;

public class Inventory {
    private HashMap<String, Integer> ingredients;

    public Inventory() {
        ingredients = new HashMap<>();
        loadInitialStock();
    }

    // Method to update the quantity of an ingredient in the inventory
    public void updateIngredient(String ingredient, int quantity) {
        int currentQuantity = ingredients.getOrDefault(ingredient, 0);
        ingredients.put(ingredient, currentQuantity + quantity);
    }

    // Method to get the quantity of an ingredient currently in stock
    public int getIngredientQuantity(String ingredient) {
        return ingredients.getOrDefault(ingredient, 0);
    }

    // Method to load initial stock for the kitchen
    private void loadInitialStock() {
        // Initial stock for some ingredients
        updateIngredient("Rice", 50);
        updateIngredient("Fish", 40);
        updateIngredient("Seaweed", 30);
        updateIngredient("Soy Sauce", 20);
        updateIngredient("Wasabi", 15);
        updateIngredient("Egg", 50);
        updateIngredient("Pork", 50);
        updateIngredient("Noodles", 50);
        updateIngredient("Flour", 50);
        updateIngredient("Tofu", 50);
        updateIngredient("Octopus", 50);
        updateIngredient("Chicken", 50);
    }

    // Method to provide a map of all ingredients and their quantities
    public HashMap<String, Integer> getIngredients() {
        return ingredients;
    }
}
