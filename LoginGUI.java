package CSC131;

import CSC131.AdminGUI;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoginGUI extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;

    public LoginGUI() {
        setTitle("Login");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(3, 2));

        JLabel usernameLabel = new JLabel("Username:");
        JLabel passwordLabel = new JLabel("Password:");

        usernameField = new JTextField();
        passwordField = new JPasswordField();

        loginButton = new JButton("Login");
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                // Check if the user is an admin
                if (username.equals("admin") && password.equals("admin")) {
                    dispose(); // Close the login window
                    new AdminGUI(); // Open the admin page
                } else if (username.equals("customer") && password.equals("password")) {
                    dispose(); // Close the login window
                    new RestaurantGUI(); // Open the restaurant menu page
                } else {
                    JOptionPane.showMessageDialog(LoginGUI.this, "Invalid username or password", "Login Failed", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(new JLabel()); // Placeholder for alignment
        panel.add(loginButton);

        add(panel);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginGUI loginGUI = new LoginGUI();
            // Assuming successful login with admin credentials, create AdminGUI
            // You can modify this logic based on your authentication mechanism
            AdminGUI adminGUI = new AdminGUI();
            // Pass the AdminGUI instance to RestaurantGUI
            new RestaurantGUI(adminGUI);
        });
    }
}
