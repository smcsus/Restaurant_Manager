package CSC131;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminGUI extends JFrame {
    private Inventory inventory;
    private RecipeManager recipeManager;
    private double totalRevenue; // Variable to store the total revenue
    private List<Map<MenuItem, Integer>> allOrderedItems; // List to store all ordered items
    private int orderCounter; // Counter to keep track of the order number

    public AdminGUI() {
        this.inventory = new Inventory();
        this.recipeManager = new RecipeManager();
        this.totalRevenue = 0.0; // Initialize total revenue to 0
        this.allOrderedItems = new ArrayList<>(); // Initialize the list for all ordered items
        this.orderCounter = 0; // Initialize the order counter

        setTitle("Admin Page");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create the back button
        JButton backButton = new JButton("Back to Login");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close the admin page
                new LoginGUI(); // Open the login page
            }
        });

        // Create the tabbed pane
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Inventory", createInventoryPanel());
        tabbedPane.addTab("Recipes", createRecipePanel());
        tabbedPane.addTab("Subtotal", createSubtotalPanel());
        tabbedPane.addTab("Total Orders", createTotalOrdersPanel());
        tabbedPane.addTab("Ordered Items", createOrderedItemsPanel());
        tabbedPane.addTab("Update Inventory", createUpdateInventoryPanel());

        // Add components to the frame
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        topPanel.add(backButton);
        add(topPanel, BorderLayout.NORTH);
        add(tabbedPane, BorderLayout.CENTER);

        setVisible(true);
    }

    public JPanel createInventoryPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);

        JButton refreshButton = new JButton("Refresh Inventory");
        refreshButton.addActionListener(e -> {
            textArea.setText("Inventory Levels:\n");
            for (Map.Entry<String, Integer> entry : inventory.getIngredients().entrySet()) {
                textArea.append(entry.getKey() + ": " + entry.getValue() + "\n");
            }
        });

        panel.add(new JScrollPane(textArea), BorderLayout.CENTER);
        panel.add(refreshButton, BorderLayout.SOUTH);

        return panel;
    }

    public JPanel createRecipePanel() {
        JPanel panel = new JPanel(new BorderLayout());

        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);

        JButton refreshButton = new JButton("Refresh Recipes");
        refreshButton.addActionListener(e -> {
            textArea.setText("Recipes:\n");
            for (Map.Entry<String, HashMap<String, Integer>> entry : recipeManager.getRecipes().entrySet()) {
                String dishName = entry.getKey();
                HashMap<String, Integer> recipe = entry.getValue();
                int minDishes = calculateMinDishes(recipe);
                textArea.append(dishName + ": " + minDishes + " dishes can be made\n");
            }
        });

        panel.add(new JScrollPane(textArea), BorderLayout.CENTER);
        panel.add(refreshButton, BorderLayout.SOUTH);

        return panel;
    }

    // Method to calculate the minimum number of dishes that can be made based on inventory
    private int calculateMinDishes(HashMap<String, Integer> recipe) {
        int minDishes = Integer.MAX_VALUE;
        for (Map.Entry<String, Integer> entry : recipe.entrySet()) {
            String ingredient = entry.getKey();
            int requiredQuantity = entry.getValue();
            int availableQuantity = inventory.getIngredientQuantity(ingredient);
            int possibleDishes = availableQuantity / requiredQuantity;
            if (possibleDishes < minDishes) {
                minDishes = possibleDishes;
            }
        }
        return minDishes;
    }

    public JPanel createSubtotalPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);

        JButton refreshButton = new JButton("Refresh Subtotal");
        refreshButton.addActionListener(e -> {
            double subtotal = calculateSubtotal(); // Calculate the subtotal
            textArea.setText("Total Revenue:\n");
            textArea.append("$" + subtotal); // Display the total revenue
        });

        panel.add(new JScrollPane(textArea), BorderLayout.CENTER);
        panel.add(refreshButton, BorderLayout.SOUTH);

        return panel;
    }

    public JPanel createTotalOrdersPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);

        JButton refreshButton = new JButton("Refresh Total Orders");
        refreshButton.addActionListener(e -> {
            textArea.setText("Total Orders: " + orderCounter);
        });

        panel.add(new JScrollPane(textArea), BorderLayout.CENTER);
        panel.add(refreshButton, BorderLayout.SOUTH);

        return panel;
    }

    public JPanel createOrderedItemsPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);

        JButton refreshButton = new JButton("Refresh Ordered Items");
        refreshButton.addActionListener(e -> {
            textArea.setText("All Ordered Items:\n");
            for (int i = 0; i < allOrderedItems.size(); i++) {
                Map<MenuItem, Integer> order = allOrderedItems.get(i);
                textArea.append("Customer " + (i + 1) + " Ordered: ");
                List<String> orderedItemsNames = new ArrayList<>();
                for (Map.Entry<MenuItem, Integer> entry : order.entrySet()) {
                    MenuItem menuItem = entry.getKey();
                    orderedItemsNames.add(menuItem.getName());
                }
                textArea.append(String.join(", ", orderedItemsNames) + "\n");
            }
        });

        panel.add(new JScrollPane(textArea), BorderLayout.CENTER);
        panel.add(refreshButton, BorderLayout.SOUTH);

        return panel;
    }


    // Method to calculate subtotal based on customer orders
    private double calculateSubtotal() {
        double subtotal = 0.0;
        for (Map<MenuItem, Integer> order : allOrderedItems) {
            for (Map.Entry<MenuItem, Integer> entry : order.entrySet()) {
                MenuItem menuItem = entry.getKey();
                int quantity = entry.getValue();
                subtotal += menuItem.getPrice() * quantity;
            }
        }
        return subtotal;
    }

    // Method to update inventory based on customer order
    public void updateInventory(Map<MenuItem, Integer> orderedItems) {
        orderCounter++; // Increment order counter
        this.allOrderedItems.add(orderedItems); // Add the ordered items to the list
        for (Map.Entry<MenuItem, Integer> entry : orderedItems.entrySet()) {
            MenuItem menuItem = entry.getKey();
            int quantity = entry.getValue();
            Map<String, Integer> ingredients = menuItem.getIngredients();
            for (Map.Entry<String, Integer> ingredientEntry : ingredients.entrySet()) {
                String ingredient = ingredientEntry.getKey();
                int ingredientQuantity = ingredientEntry.getValue() * quantity;
                inventory.updateIngredient(ingredient, -ingredientQuantity); // Reduce the inventory quantity
            }
        }
    }

    public JPanel createUpdateInventoryPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(0, 2));
        JTextField ingredientField = new JTextField();
        JTextField quantityField = new JTextField();
        inputPanel.add(new JLabel("Ingredient:"));
        inputPanel.add(ingredientField);
        inputPanel.add(new JLabel("Quantity:"));
        inputPanel.add(quantityField);

        JButton updateButton = new JButton("Update Inventory");
        JTextArea statusArea = new JTextArea();
        statusArea.setEditable(false);
        updateButton.addActionListener(e -> {
            String ingredient = ingredientField.getText();
            String quantityText = quantityField.getText();
            try {
                int quantity = Integer.parseInt(quantityText);
                inventory.updateIngredient(ingredient, quantity);
                statusArea.setText("Inventory updated successfully.");
            } catch (NumberFormatException ex) {
                statusArea.setText("Invalid quantity. Please enter a valid number.");
            }
        });

        panel.add(inputPanel, BorderLayout.NORTH);
        panel.add(updateButton, BorderLayout.CENTER);
        panel.add(new JScrollPane(statusArea), BorderLayout.SOUTH);

        return panel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(AdminGUI::new);
    }
}
