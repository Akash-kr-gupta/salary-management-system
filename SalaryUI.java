package day1;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.sql.*;

public class SalaryUI {

    static final String URL = "jdbc:mysql://localhost:3306/student";
    static final String USER = "root";
    static final String PWD = "abc123";

    public static void main(String[] args) {
        JFrame frame = new JFrame("Salary Calculator");

        // Labels and text fields
        JLabel l1 = new JLabel("Employee ID:");
        JLabel l2 = new JLabel("Basic Salary:");
        JLabel l3 = new JLabel("HRA:");
        JLabel l4 = new JLabel("Allowances:");
        JLabel l5 = new JLabel("Bonus:");

        JTextField t1 = new JTextField(); // emp_id
        JTextField t2 = new JTextField(); // basic
        JTextField t3 = new JTextField(); // hra
        JTextField t4 = new JTextField(); // allowances
        JTextField t5 = new JTextField(); // bonus

        JButton calcBtn = new JButton("Add Salary Record");
        JButton viewBtn = new JButton("View Salary Records");

        // Set bounds for UI components
        l1.setBounds(30, 20, 150, 30);
        l2.setBounds(30, 60, 150, 30);
        l3.setBounds(30, 100, 150, 30);
        l4.setBounds(30, 140, 150, 30);
        l5.setBounds(30, 180, 150, 30);

        t1.setBounds(200, 20, 150, 30);
        t2.setBounds(200, 60, 150, 30);
        t3.setBounds(200, 100, 150, 30);
        t4.setBounds(200, 140, 150, 30);
        t5.setBounds(200, 180, 150, 30);

        calcBtn.setBounds(30, 230, 160, 30);
        viewBtn.setBounds(200, 230, 160, 30);

        // Add components to frame
        frame.add(l1); frame.add(t1);
        frame.add(l2); frame.add(t2);
        frame.add(l3); frame.add(t3);
        frame.add(l4); frame.add(t4);
        frame.add(l5); frame.add(t5);
        frame.add(calcBtn);
        frame.add(viewBtn);

        frame.setSize(420, 330);
        frame.setLayout(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Add Salary Record
        calcBtn.addActionListener(e -> {
            try {
                // Get user input values
                int empId = Integer.parseInt(t1.getText());
                double basic = Double.parseDouble(t2.getText());
                double hra = Double.parseDouble(t3.getText());
                double allowances = Double.parseDouble(t4.getText());
                double bonus = Double.parseDouble(t5.getText());

                // Validate positive salary values
                if (basic < 0 || hra < 0 || allowances < 0 || bonus < 0) {
                    JOptionPane.showMessageDialog(frame, "Error: Salary values cannot be negative.");
                    return;
                }

                // Calculate total salary
                double totalSalary = basic + hra + allowances + bonus;

                try (Connection con = DriverManager.getConnection(URL, USER, PWD)) {
                    // Prepare SQL insert query
                    String insert = "INSERT INTO salary (employee_id, basic_salary, hra, allowances, bonus, total_salary) VALUES (?, ?, ?, ?, ?, ?)";
                    PreparedStatement ps = con.prepareStatement(insert);
                    ps.setInt(1, empId);
                    ps.setDouble(2, basic);
                    ps.setDouble(3, hra);
                    ps.setDouble(4, allowances);
                    ps.setDouble(5, bonus);
                    ps.setDouble(6, totalSalary);

                    // Execute insert
                    int rows = ps.executeUpdate();
                    if (rows > 0) {
                        JOptionPane.showMessageDialog(frame, "Salary added successfully! Total: " + totalSalary);
                        // Clear fields after successful entry
                        t1.setText(""); t2.setText(""); t3.setText(""); t4.setText(""); t5.setText("");
                    } else {
                        JOptionPane.showMessageDialog(frame, "Failed to add salary.");
                    }
                } catch (SQLException ex) {
                    // SQL related error
                    JOptionPane.showMessageDialog(frame, "Database Error: " + ex.getMessage());
                    ex.printStackTrace();
                }
            } catch (NumberFormatException ex) {
                // Handle invalid input
                JOptionPane.showMessageDialog(frame, "Error: Please enter valid numbers for salary fields.");
                ex.printStackTrace();
            } catch (Exception ex) {
                // General exception handler
                JOptionPane.showMessageDialog(frame, "Unexpected Error: " + ex.getMessage());
                ex.printStackTrace();
            }
        });

        // View Salary Records
        viewBtn.addActionListener(e -> {
            JFrame viewFrame = new JFrame("All Salary Records");

            String[] columns = {"Salary ID", "Emp ID", "Basic", "HRA", "Allowances", "Bonus", "Total Salary"};
            DefaultTableModel model = new DefaultTableModel(columns, 0);
            JTable table = new JTable(model);
            JScrollPane scrollPane = new JScrollPane(table);
            scrollPane.setBounds(20, 20, 700, 300);

            try (Connection con = DriverManager.getConnection(URL, USER, PWD)) {
                String query = "SELECT * FROM salary";
                ResultSet rs = con.createStatement().executeQuery(query);
                while (rs.next()) {
                    model.addRow(new Object[]{
                        rs.getInt("salary_id"),
                        rs.getInt("employee_id"),
                        rs.getDouble("basic_salary"),
                        rs.getDouble("hra"),
                        rs.getDouble("allowances"),
                        rs.getDouble("bonus"),
                        rs.getDouble("total_salary")
                    });
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(viewFrame, "Error: " + ex.getMessage());
            }

            viewFrame.add(scrollPane);
            viewFrame.setSize(760, 400);
            viewFrame.setLayout(null);
            viewFrame.setVisible(true);
            viewFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        });
    }
}
