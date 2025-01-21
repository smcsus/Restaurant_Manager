package CSC131;
import java.util.HashMap;
import java.util.Map;

public class Order {
    private HashMap<MenuItem, Integer> orderedItems;
    private double totalCost;

    public Order() {
        orderedItems = new HashMap<>();
        totalCost = 0.0;
    }

    // Adds an item to the order
    public void addItem(MenuItem item, int quantity) {
        if (orderedItems.containsKey(item)) {
            quantity += orderedItems.get(item);  // Update the quantity if already present
        }
        orderedItems.put(item, quantity);
        totalCost += item.getPrice() * quantity;
    }

    // Removes an item from the order
    public void removeItem(MenuItem item) {
        if (orderedItems.containsKey(item)) {
            int currentQuantity = orderedItems.get(item);
            if (currentQuantity > 1) {
                orderedItems.put(item, currentQuantity - 1);
                totalCost -= item.getPrice();
            } else {
                orderedItems.remove(item);
                totalCost -= item.getPrice();
            }
        }
    }

    // Gets the total cost of the order
    public double getTotalCost() {
        return totalCost;
    }

    // Provides a map of all items in the order with their quantities
    public Map<MenuItem, Integer> getOrderedItems() {
        return orderedItems;
    }

    // Clears the order
    public void clear() {
        orderedItems.clear();
        totalCost = 0.0;
    }
}
