package day1;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.math.BigDecimal;

public class TaxInsertUI {

    static final String URL = "jdbc:mysql://localhost:3306/student";
    static final String USER = "root";
    static final String PWD = "abc123";

    public static void main(String[] args) {
        // Load MySQL JDBC driver
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "MySQL JDBC Driver not found.");
            e.printStackTrace();
            return;
        }

        // Create and set up the window
        JFrame frame = new JFrame("Insert Tax Record");
        frame.setSize(400, 300);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        // Create components
        JLabel empIdLabel = new JLabel("Employee ID:");
        JLabel taxYearLabel = new JLabel("Tax Year:");
        JLabel totalTaxLabel = new JLabel("Total Tax:");

        JTextField empIdField = new JTextField();
        JTextField taxYearField = new JTextField();
        JTextField totalTaxField = new JTextField();

        JButton submitButton = new JButton("Submit");

        // Set bounds for components
        empIdLabel.setBounds(30, 30, 100, 30);
        empIdField.setBounds(150, 30, 200, 30);
        taxYearLabel.setBounds(30, 70, 100, 30);
        taxYearField.setBounds(150, 70, 200, 30);
        totalTaxLabel.setBounds(30, 110, 100, 30);
        totalTaxField.setBounds(150, 110, 200, 30);
        submitButton.setBounds(150, 160, 100, 30);

        // Add components to frame
        frame.add(empIdLabel);
        frame.add(empIdField);
        frame.add(taxYearLabel);
        frame.add(taxYearField);
        frame.add(totalTaxLabel);
        frame.add(totalTaxField);
        frame.add(submitButton);

        // Add action listener for submit button
        submitButton.addActionListener(e -> {
            String empId = empIdField.getText().trim();
            String taxYear = taxYearField.getText().trim();
            String totalTax = totalTaxField.getText().trim();

            // Validate inputs
            if (empId.isEmpty() || taxYear.isEmpty() || totalTax.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "All fields are required.");
                return;
            }

            try {
                // Validate numeric inputs
                int empIdInt = Integer.parseInt(empId);
                int taxYearInt = Integer.parseInt(taxYear);
                BigDecimal totalTaxDec = new BigDecimal(totalTax);

                // Establish database connection
                try (Connection con = DriverManager.getConnection(URL, USER, PWD)) {
                    String query = "INSERT INTO Tax (employee_id, tax_year, total_tax) VALUES (?, ?, ?)";
                    try (PreparedStatement ps = con.prepareStatement(query)) {
                        ps.setInt(1, empIdInt);
                        ps.setInt(2, taxYearInt);
                        ps.setBigDecimal(3, totalTaxDec);

                        int result = ps.executeUpdate();
                        if (result > 0) {
                            JOptionPane.showMessageDialog(frame, "Tax record inserted successfully.");
                            frame.dispose(); // Close the window after successful insertion
                        } else {
                            JOptionPane.showMessageDialog(frame, "Failed to insert tax record.");
                        }
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(frame, "Database error: " + ex.getMessage());
                    ex.printStackTrace();
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Please enter valid numeric values for Employee ID, Tax Year, and Total Tax.");
                ex.printStackTrace();
            }
        });

        // Display the window
        frame.setVisible(true);
    }
}
