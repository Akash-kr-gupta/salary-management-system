package day1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InsertRecordUI {

    public static void main(String[] args) {
        // Create the frame
        JFrame frame = new JFrame("Insert Record");
        frame.setSize(400, 400);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null); // Center the window

        // Title label
        JLabel titleLabel = new JLabel("Insert Account Record");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setBounds(120, 20, 200, 30);
        frame.add(titleLabel);

        // Fields for account record
        JLabel nameLabel = new JLabel("Account Name:");
        nameLabel.setBounds(50, 70, 100, 30);
        frame.add(nameLabel);

        JTextField nameField = new JTextField();
        nameField.setBounds(160, 70, 180, 30);
        frame.add(nameField);

        JLabel balanceLabel = new JLabel("Balance:");
        balanceLabel.setBounds(50, 120, 100, 30);
        frame.add(balanceLabel);

        JTextField balanceField = new JTextField();
        balanceField.setBounds(160, 120, 180, 30);
        frame.add(balanceField);

        JLabel accNoLabel = new JLabel("Account Number:");
        accNoLabel.setBounds(50, 170, 120, 30);
        frame.add(accNoLabel);

        JTextField accNoField = new JTextField();
        accNoField.setBounds(160, 170, 180, 30);
        frame.add(accNoField);

        // Insert button
        JButton insertBtn = new JButton("Insert Account");
        insertBtn.setBounds(120, 250, 150, 30);
        frame.add(insertBtn);

        // Action listener for the insert button
        insertBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get input values from the text fields
                String name = nameField.getText();
                String balanceStr = balanceField.getText();
                String accNoStr = accNoField.getText();

                // Validate input
                if (name.isEmpty() || balanceStr.isEmpty() || accNoStr.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please fill all fields", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    int balance = Integer.parseInt(balanceStr);
                    int accNo = Integer.parseInt(accNoStr);

                    // Insert data into the database
                    insertAccount(accNo, name, balance);

                    // Show success message
                    JOptionPane.showMessageDialog(frame, "Account inserted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

                    // Clear input fields after insertion
                    nameField.setText("");
                    balanceField.setText("");
                    accNoField.setText("");

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Please enter valid numbers for balance and account number", "Input Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Show the frame
        frame.setVisible(true);
    }

    private static void insertAccount(int account_No, String name, int balance) {
        // Database connection details
        String url = "jdbc:mysql://localhost:3306/student"; // Adjust the database URL if needed
        String user = "root"; // Your database username
        String password = ""; // Your database password

        String query = "INSERT INTO account (account_no, name, balance) VALUES (?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, account_No);
            preparedStatement.setString(2, name);
            preparedStatement.setInt(3, balance);

            // Execute the insert statement
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error inserting account record into the database", "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
