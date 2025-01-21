package CSC131;
import java.util.ArrayList;
import java.util.Map;

public class Menu {
    private ArrayList<MenuItem> items;

    public Menu() {
        items = new ArrayList<>();
        loadSampleMenuItems();
    }

    public void addItem(MenuItem item) {
        items.add(item);
    }

    public ArrayList<MenuItem> getItems() {
        return items;
    }

    private void loadSampleMenuItems() {
        // Adding a sample list of 10 Japanese-style dishes
        items.add(new MenuItem("Sushi", 12.99, Map.of("Rice", 1, "Fish", 2)));
        items.add(new MenuItem("Sashimi", 14.99, Map.of("Fish", 2)));
        items.add(new MenuItem("Tempura", 11.99, Map.of("Fish", 1, "Flour", 1)));
        items.add(new MenuItem("Ramen", 9.99, Map.of("Noodles", 1, "Egg", 1, "Pork", 1)));
        items.add(new MenuItem("Udon", 8.99, Map.of("Noodles", 1)));
        items.add(new MenuItem("Takoyaki", 6.99, Map.of("Octopus", 1, "Flour", 1)));
        items.add(new MenuItem("Miso Soup", 4.99, Map.of("Soy Sauce", 1, "Tofu", 1)));
        items.add(new MenuItem("Yakitori", 7.99, Map.of("Chicken", 1, "Soy Sauce", 1)));
        items.add(new MenuItem("Onigiri", 3.99, Map.of("Rice", 1, "Seaweed", 1)));
        items.add(new MenuItem("Teriyaki Chicken", 10.99, Map.of("Chicken", 1, "Soy Sauce", 1)));
    }
}
