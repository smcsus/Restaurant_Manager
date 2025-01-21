
package CSC131;
import java.util.Map;

public class MenuItem {
    private String name;
    private double price;
    private Map<String, Integer> ingredients; // Map to store required ingredients and their quantities

    public MenuItem(String name, double price, Map<String, Integer> ingredients) {
        this.name = name;
        this.price = price;
        this.ingredients = ingredients;
    }

    // Getter method for name
    public String getName() {
        return name;
    }

    // Getter method for price
    public double getPrice() {
        return price;
    }

    // Getter method for ingredients
    public Map<String, Integer> getIngredients() {
        return ingredients;
    }
}
