package CSC131;
import CSC131.AdminGUI;
import CSC131.Inventory;
import CSC131.Order;
import CSC131.MenuItem;
import CSC131.Menu;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Map;

public class RestaurantGUI extends JFrame {
    private JFrame frame;
    private Menu menu;
    private Order order;
    private AdminGUI adminGUI; // Reference to the AdminGUI

    public RestaurantGUI() {
        initialize();
    }

    public RestaurantGUI(AdminGUI adminGUI) {
        this.adminGUI = adminGUI;
        initialize();
    }

    private void initialize() {
        this.menu = new Menu(); // Assumes Menu is already populated
        this.order = new Order();

        frame = new JFrame("Restaurant Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        JPanel loginPanel = new JPanel(new GridLayout(3, 2)); // Username, Password, and Login button
        JLabel usernameLabel = new JLabel("Username:");
        JTextField usernameField = new JTextField();
        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField();
        JButton loginButton = new JButton("Login");

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                // Check if the user is an admin
                if (username.equals("admin") && password.equals("admin")) {
                    // Open admin panel
                    frame.getContentPane().removeAll(); // Remove login panel
                    frame.add(adminGUI.createInventoryPanel(), BorderLayout.CENTER);
                    frame.revalidate();
                    frame.repaint();
                } else if (username.equals("customer") && password.equals("password")) {
                    // Open customer panel
                    frame.getContentPane().removeAll(); // Remove login panel
                    frame.add(createCustomerPanel(), BorderLayout.CENTER);
                    frame.revalidate();
                    frame.repaint();
                } else {
                    JOptionPane.showMessageDialog(frame, "Invalid username or password", "Login Failed", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        loginPanel.add(usernameLabel);
        loginPanel.add(usernameField);
        loginPanel.add(passwordLabel);
        loginPanel.add(passwordField);
        loginPanel.add(new JLabel()); // Placeholder for alignment
        loginPanel.add(loginButton);

        frame.add(loginPanel, BorderLayout.CENTER);

        frame.setVisible(true);
    }

    private JPanel createCustomerPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);

        JPanel menuPanel = new JPanel(new GridLayout(0, 1));
        for (MenuItem item : menu.getItems()) {
            JButton button = new JButton(item.getName() + " - $" + item.getPrice());
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    order.addItem(item, 1);  // Add item to the order
                    JOptionPane.showMessageDialog(frame, item.getName() + " added to order.");

                    // Update inventory in AdminGUI when order is placed
                    if (adminGUI != null) {
                        adminGUI.updateInventory(order.getOrderedItems());
                    }

                    // Refresh order display
                    refreshOrderDisplay(textArea);
                }
            });
            menuPanel.add(button);
        }

        JButton placeOrderButton = new JButton("Place Order");
        placeOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Add logic to place order
                // For now, let's just display a message
                JOptionPane.showMessageDialog(frame, "Order placed successfully!");
            }
        });

        JButton refreshButton = new JButton("Refresh Order");
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Clear the order
                order.clear();
                // Clear the text area
                textArea.setText("");
            }
        });

        panel.add(new JScrollPane(menuPanel), BorderLayout.WEST);
        panel.add(new JScrollPane(textArea), BorderLayout.CENTER);
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2));
        buttonPanel.add(refreshButton);
        buttonPanel.add(placeOrderButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    private void refreshOrderDisplay(JTextArea textArea) {
        textArea.setText("Current Order:\n");
        double subtotal = 0.0; // Initialize subtotal
        for (Map.Entry<MenuItem, Integer> entry : order.getOrderedItems().entrySet()) {
            MenuItem menuItem = entry.getKey();
            int quantity = entry.getValue();
            double itemPrice = menuItem.getPrice(); // Retrieve the price of the item
            subtotal += itemPrice * quantity; // Update subtotal
            textArea.append(menuItem.getName() + " x " + quantity + " = $" + (itemPrice * quantity) + "\n");
        }
        textArea.append("Total: $" + subtotal);
    }

    public static void main(String[] args) {
        AdminGUI adminGUI = new AdminGUI();
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new RestaurantGUI(adminGUI);
            }
        });
    }
}

