package day1;

import javax.swing.*;
import java.sql.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class EmployeeListUI {

    static final String URL = "jdbc:mysql://localhost:3306/student";
    static final String USER = "root";
    static final String PWD = "abc123";

    public static void main(String[] args) {
        // Create the frame for viewing employee list
        JFrame frame = new JFrame("Employee List");
        frame.setSize(800, 500);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null); // Center the window
        frame.setLayout(new BorderLayout());

        // Table column names
        String[] columnNames = {
            "Employee ID", "First Name", "Last Name", "DOB", "Gender",
            "Email", "Phone", "Hire Date", "Job Title", "Dept ID", "Status"
        };

        // Create table model and JTable
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        JTable employeeTable = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(employeeTable);

        // Add table to the center of the frame
        frame.add(scrollPane, BorderLayout.CENTER);

        // Close button at the bottom
        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> frame.dispose());
        JPanel bottomPanel = new JPanel();
        bottomPanel.add(closeButton);
        frame.add(bottomPanel, BorderLayout.SOUTH);

        // Fetch and populate employee data from the database
        try (Connection con = DriverManager.getConnection(URL, USER, PWD)) {
            String query = "SELECT * FROM Employee";
            try (PreparedStatement ps = con.prepareStatement(query);
                 ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    int employeeId = rs.getInt("employee_id");
                    String firstName = rs.getString("first_name");
                    String lastName = rs.getString("last_name");
                    String dob = rs.getString("date_of_birth");
                    String gender = rs.getString("gender");
                    String email = rs.getString("email");
                    String phone = rs.getString("phone_number");
                    String hireDate = rs.getString("hire_date");
                    String jobTitle = rs.getString("job_title");
                    int deptId = rs.getInt("department_id");
                    String status = rs.getString("status");

                    model.addRow(new Object[]{
                        employeeId, firstName, lastName, dob, gender,
                        email, phone, hireDate, jobTitle, deptId, status
                    });
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error fetching employee data: " + ex.getMessage());
        }

        frame.setVisible(true);
    }
}
