import CSC131.AdminGUI;
import CSC131.Inventory;
import CSC131.Order;
import CSC131.RestaurantGUI;

import javax.swing.*;

public static void main(String[] args) {
    Inventory inventory = new Inventory(); // Create Inventory instance
    Order order = new Order(); // No need to pass Inventory instance to Order constructor anymore
    AdminGUI adminGUI = new AdminGUI(); // Create AdminGUI instance
    SwingUtilities.invokeLater(new Runnable() {
        public void run() {
            new RestaurantGUI(adminGUI); // Pass the AdminGUI instance to RestaurantGUI
        }
    });
}
