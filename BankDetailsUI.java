package day1;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class BankDetailsUI {

    static final String URL = "jdbc:mysql://localhost:3306/student";
    static final String USER = "root";
    static final String PWD = "abc123";

    public static void main(String[] args) {
        JFrame frame = new JFrame("Insert Bank Details");
        frame.setSize(400, 300);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        JLabel empIdLabel = new JLabel("Employee ID:");
        JLabel bankNameLabel = new JLabel("Bank Name:");
        JLabel accountNumberLabel = new JLabel("Account Number:");
        JLabel accountTypeLabel = new JLabel("Account Type:");
        JLabel ifscCodeLabel = new JLabel("IFSC Code:");

        JTextField empIdField = new JTextField();
        JTextField bankNameField = new JTextField();
        JTextField accountNumberField = new JTextField();
        JComboBox<String> accountTypeComboBox = new JComboBox<>(new String[] { "Savings", "Current" });
        JTextField ifscCodeField = new JTextField();

        JButton submitButton = new JButton("Submit");

        empIdLabel.setBounds(30, 30, 100, 30);
        empIdField.setBounds(150, 30, 200, 30);
        bankNameLabel.setBounds(30, 70, 100, 30);
        bankNameField.setBounds(150, 70, 200, 30);
        accountNumberLabel.setBounds(30, 110, 100, 30);
        accountNumberField.setBounds(150, 110, 200, 30);
        accountTypeLabel.setBounds(30, 150, 100, 30);
        accountTypeComboBox.setBounds(150, 150, 200, 30);
        ifscCodeLabel.setBounds(30, 190, 100, 30);
        ifscCodeField.setBounds(150, 190, 200, 30);
        submitButton.setBounds(150, 230, 100, 30);

        frame.add(empIdLabel);
        frame.add(empIdField);
        frame.add(bankNameLabel);
        frame.add(bankNameField);
        frame.add(accountNumberLabel);
        frame.add(accountNumberField);
        frame.add(accountTypeLabel);
        frame.add(accountTypeComboBox);
        frame.add(ifscCodeLabel);
        frame.add(ifscCodeField);
        frame.add(submitButton);

        submitButton.addActionListener(e -> {
            String empId = empIdField.getText();
            String bankName = bankNameField.getText();
            String accountNumber = accountNumberField.getText();
            String accountType = (String) accountTypeComboBox.getSelectedItem();
            String ifscCode = ifscCodeField.getText();

            try (Connection con = DriverManager.getConnection(URL, USER, PWD)) {
                String query = "INSERT INTO bank_details (employee_id, bank_name, account_number, account_type, ifsc_code) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement ps = con.prepareStatement(query);
                ps.setString(1, empId);
                ps.setString(2, bankName);
                ps.setString(3, accountNumber);
                ps.setString(4, accountType);
                ps.setString(5, ifscCode);

                int result = ps.executeUpdate();
                if (result > 0) {
                    JOptionPane.showMessageDialog(frame, "Bank details inserted successfully.");
                    frame.dispose(); // Close the window after successful insertion
                } else {
                    JOptionPane.showMessageDialog(frame, "Failed to insert bank details.");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage());
            }
        });

        frame.setVisible(true);
    }
}
