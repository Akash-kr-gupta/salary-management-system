package day1;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class PayrollInsertUI {

    // MySQL database connection details
    static final String URL = "jdbc:mysql://localhost:3306/student"; // Change to your DB name
    static final String USER = "root"; // Database username
    static final String PWD = "abc123"; // Database password

    public static void main(String[] args) {
        JFrame frame = new JFrame("Insert Payroll Details");
        frame.setSize(400, 400);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        // Labels for input fields
        JLabel employeeIdLabel = new JLabel("Employee ID:");
        JLabel salaryIdLabel = new JLabel("Salary ID:");
        JLabel payPeriodStartLabel = new JLabel("Pay Period Start:");
        JLabel payPeriodEndLabel = new JLabel("Pay Period End:");
        JLabel grossSalaryLabel = new JLabel("Gross Salary:");
        JLabel totalDeductionsLabel = new JLabel("Total Deductions:");

        // Input fields for data entry
        JTextField employeeIdField = new JTextField();
        JTextField salaryIdField = new JTextField();
        JTextField payPeriodStartField = new JTextField();
        JTextField payPeriodEndField = new JTextField();
        JTextField grossSalaryField = new JTextField();
        JTextField totalDeductionsField = new JTextField();

        JButton submitButton = new JButton("Submit");

        // Setting bounds for each component
        employeeIdLabel.setBounds(30, 30, 100, 30);
        employeeIdField.setBounds(150, 30, 200, 30);
        salaryIdLabel.setBounds(30, 70, 100, 30);
        salaryIdField.setBounds(150, 70, 200, 30);
        payPeriodStartLabel.setBounds(30, 110, 150, 30);
        payPeriodStartField.setBounds(150, 110, 200, 30);
        payPeriodEndLabel.setBounds(30, 150, 150, 30);
        payPeriodEndField.setBounds(150, 150, 200, 30);
        grossSalaryLabel.setBounds(30, 190, 100, 30);
        grossSalaryField.setBounds(150, 190, 200, 30);
        totalDeductionsLabel.setBounds(30, 230, 150, 30);
        totalDeductionsField.setBounds(150, 230, 200, 30);
        submitButton.setBounds(150, 270, 100, 30);

        // Adding components to the frame
        frame.add(employeeIdLabel);
        frame.add(employeeIdField);
        frame.add(salaryIdLabel);
        frame.add(salaryIdField);
        frame.add(payPeriodStartLabel);
        frame.add(payPeriodStartField);
        frame.add(payPeriodEndLabel);
        frame.add(payPeriodEndField);
        frame.add(grossSalaryLabel);
        frame.add(grossSalaryField);
        frame.add(totalDeductionsLabel);
        frame.add(totalDeductionsField);
        frame.add(submitButton);

        // Submit button action listener
        submitButton.addActionListener(e -> {
            // Fetch values from the text fields
            String employeeId = employeeIdField.getText();
            String salaryId = salaryIdField.getText();
            String payPeriodStart = payPeriodStartField.getText();
            String payPeriodEnd = payPeriodEndField.getText();
            double grossSalary = Double.parseDouble(grossSalaryField.getText());
            double totalDeductions = Double.parseDouble(totalDeductionsField.getText());
            double netSalary = grossSalary - totalDeductions;

            // Database insertion logic
            try (Connection con = DriverManager.getConnection(URL, USER, PWD)) {
                String query = "INSERT INTO Payroll (employee_id, salary_id, pay_period_start, pay_period_end, gross_salary, total_deductions, net_salary) " +
                               "VALUES (?, ?, ?, ?, ?, ?, ?)";
                try (PreparedStatement ps = con.prepareStatement(query)) {
                    ps.setString(1, employeeId);
                    ps.setString(2, salaryId);
                    ps.setString(3, payPeriodStart);
                    ps.setString(4, payPeriodEnd);
                    ps.setDouble(5, grossSalary);
                    ps.setDouble(6, totalDeductions);
                    ps.setDouble(7, netSalary);

                    int result = ps.executeUpdate();
                    if (result > 0) {
                        JOptionPane.showMessageDialog(frame, "Payroll details inserted successfully.");
                        frame.dispose(); // Close the window after successful insertion
                    } else {
                        JOptionPane.showMessageDialog(frame, "Failed to insert payroll details.");
                    }
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage());
            }
        });

        frame.setVisible(true);
    }
}
