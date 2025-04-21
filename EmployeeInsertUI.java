package day1;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class EmployeeInsertUI {

    static final String URL = "jdbc:mysql://localhost:3306/student";
    static final String USER = "root";
    static final String PWD = "abc123";

    public static void main(String[] args) {
        JFrame frame = new JFrame("Insert Employee Record");
        frame.setSize(500, 600);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        String[] labels = {
            "First Name:", "Last Name:", "Date of Birth (YYYY-MM-DD):", "Gender:",
            "Email:", "Phone Number:", "Hire Date (YYYY-MM-DD):", "Job Title:",
            "Department ID:", "Status:"
        };

        JTextField[] fields = new JTextField[labels.length];
        JComboBox<String> genderBox = new JComboBox<>(new String[]{"Male", "Female", "Other"});
        JComboBox<String> statusBox = new JComboBox<>(new String[]{"Active", "Inactive"});

        int y = 20;
        for (int i = 0; i < labels.length; i++) {
            JLabel label = new JLabel(labels[i]);
            label.setBounds(30, y, 200, 25);
            frame.add(label);

            if (i == 3) { // Gender ComboBox
                genderBox.setBounds(230, y, 200, 25);
                frame.add(genderBox);
            } else if (i == 9) { // Status ComboBox
                statusBox.setBounds(230, y, 200, 25);
                frame.add(statusBox);
            } else {
                fields[i] = new JTextField(); // Initialize the text field
                fields[i].setBounds(230, y, 200, 25);
                frame.add(fields[i]);
            }
            y += 40;
        }

        JButton insertBtn = new JButton("Insert Employee");
        insertBtn.setBounds(160, y + 10, 160, 30);
        frame.add(insertBtn);

        insertBtn.addActionListener(e -> {
            try (Connection con = DriverManager.getConnection(URL, USER, PWD)) {
                // Validate inputs before inserting
                for (int i = 0; i < fields.length; i++) {
                    if (i != 3 && i != 9) { // Skip gender and status as they are combo boxes
                        JTextField tf = fields[i];
                        if (tf == null || tf.getText().trim().isEmpty()) {
                            JOptionPane.showMessageDialog(frame, "All fields are required!");
                            return;
                        }
                    }
                }

                // Get values from fields and combo boxes
                String firstName = fields[0].getText();
                String lastName = fields[1].getText();
                String dob = fields[2].getText();
                String gender = (String) genderBox.getSelectedItem();
                String email = fields[4].getText();
                String phoneNumber = fields[5].getText();
                String hireDate = fields[6].getText();
                String jobTitle = fields[7].getText();
                int departmentId = Integer.parseInt(fields[8].getText());
                String status = (String) statusBox.getSelectedItem();

                // Insert query
                String query = "INSERT INTO Employee (first_name, last_name, date_of_birth, gender, email, phone_number, hire_date, job_title, department_id, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement ps = con.prepareStatement(query);

                // Set parameters for the PreparedStatement
                ps.setString(1, firstName);
                ps.setString(2, lastName);
                ps.setString(3, dob);
                ps.setString(4, gender);
                ps.setString(5, email);
                ps.setString(6, phoneNumber);
                ps.setString(7, hireDate);
                ps.setString(8, jobTitle);
                ps.setInt(9, departmentId);
                ps.setString(10, status);

                // Execute the query
                ps.executeUpdate();
                JOptionPane.showMessageDialog(frame, "Employee inserted successfully!");

                // Clear fields after insertion
                for (JTextField tf : fields) {
                    if (tf != null) {
                        tf.setText("");
                    }
                }
                genderBox.setSelectedIndex(0); // Reset gender selection
                statusBox.setSelectedIndex(0); // Reset status selection
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Error: Please enter a valid Department ID (numeric).");
            }
        });

        frame.setVisible(true);
    }
}
