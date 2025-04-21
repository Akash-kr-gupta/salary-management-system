package day1;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class DepartmentInsertUI {

    static final String URL = "jdbc:mysql://localhost:3306/student";
    static final String USER = "root";
    static final String PWD = "abc123";

    public static void main(String[] args) {
        JFrame frame = new JFrame("Insert Department");
        frame.setSize(400, 300);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        JLabel titleLabel = new JLabel("Insert Department");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setBounds(100, 20, 200, 30);
        frame.add(titleLabel);

        JLabel deptNameLabel = new JLabel("Department Name:");
        deptNameLabel.setBounds(30, 80, 150, 30);
        JTextField deptNameField = new JTextField();
        deptNameField.setBounds(180, 80, 150, 30);
        frame.add(deptNameLabel);
        frame.add(deptNameField);

        JLabel deptCodeLabel = new JLabel("Department Code:");
        deptCodeLabel.setBounds(30, 120, 150, 30);
        JTextField deptCodeField = new JTextField();
        deptCodeField.setBounds(180, 120, 150, 30);
        frame.add(deptCodeLabel);
        frame.add(deptCodeField);

        JButton submitBtn = new JButton("Submit");
        submitBtn.setBounds(150, 180, 100, 30);
        frame.add(submitBtn);

        submitBtn.addActionListener(e -> {
            String deptName = deptNameField.getText();
            String deptCode = deptCodeField.getText();

            if (deptName.isEmpty() || deptCode.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "All fields are required!");
                return;
            }

            try (Connection con = DriverManager.getConnection(URL, USER, PWD)) {
                String query = "INSERT INTO department (department_name, department_code) VALUES (?, ?)";
                PreparedStatement ps = con.prepareStatement(query);
                ps.setString(1, deptName);
                ps.setString(2, deptCode);

                int rowsAffected = ps.executeUpdate();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(frame, "Department inserted successfully!");
                    frame.dispose();
                } else {
                    JOptionPane.showMessageDialog(frame, "Error inserting department.");
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage());
            }
        });

        frame.setVisible(true);
    }
}
