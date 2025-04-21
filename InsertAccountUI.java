package day1;

import javax.swing.*;
import java.sql.*;

public class InsertAccountUI {
    static final String URL = "jdbc:mysql://localhost:3306/student";
    static final String USER = "root";
    static final String PWD = "abc123";

    public static void main(String[] args) {
        // Create a new frame for the account insertion
        JFrame frame = new JFrame("Insert Account");
        frame.setSize(400, 300);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create labels for each input field
        JLabel accNoLabel = new JLabel("Account No:");
        accNoLabel.setBounds(30, 30, 100, 25);
        frame.add(accNoLabel);

        JLabel nameLabel = new JLabel("Account Holder Name:");
        nameLabel.setBounds(30, 70, 150, 25);
        frame.add(nameLabel);

        JLabel balanceLabel = new JLabel("Balance:");
        balanceLabel.setBounds(30, 110, 100, 25);
        frame.add(balanceLabel);

        // Create input fields for each attribute
        JTextField accNoField = new JTextField();
        accNoField.setBounds(180, 30, 180, 25);
        frame.add(accNoField);

        JTextField nameField = new JTextField();
        nameField.setBounds(180, 70, 180, 25);
        frame.add(nameField);

        JTextField balanceField = new JTextField();
        balanceField.setBounds(180, 110, 180, 25);
        frame.add(balanceField);

        // Create button to insert the account
        JButton insertBtn = new JButton("Insert Account");
        insertBtn.setBounds(120, 150, 150, 30);
        frame.add(insertBtn);

        // Button click event to insert the account into the database
        insertBtn.addActionListener(e -> {
            try (Connection con = DriverManager.getConnection(URL, USER, PWD)) {
                // Validate input fields
                if (accNoField.getText().trim().isEmpty() || nameField.getText().trim().isEmpty() || balanceField.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "All fields are required!");
                    return;
                }

                // Get data from text fields
                int accNo = Integer.parseInt(accNoField.getText().trim());
                String name = nameField.getText().trim();
                int balance = Integer.parseInt(balanceField.getText().trim());

                // SQL query to insert the new account
                String sql = "INSERT INTO account (acc_no, name, balance) VALUES (?, ?, ?)";

                try (PreparedStatement ps = con.prepareStatement(sql)) {
                    ps.setInt(1, accNo);
                    ps.setString(2, name);
                    ps.setInt(3, balance);
                    ps.executeUpdate();
                }

                JOptionPane.showMessageDialog(frame, "Account inserted successfully!");
                accNoField.setText("");
                nameField.setText("");
                balanceField.setText("");

            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage());
            } catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(frame, "Please enter valid numbers for Account No and Balance.");
            }
        });

        // Show frame
        frame.setVisible(true);
    }
}
