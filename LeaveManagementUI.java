package day1;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LeaveManagementUI {
    static final String URL = "jdbc:mysql://localhost:3306/student";
    static final String USER = "root";
    static final String PWD = "abc123";

    public static void main(String[] args) {
        JFrame frame = new JFrame("Leave Management");
        frame.setSize(500, 600);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        String[] labels = {
            "Employee ID:", "Leave Type:", "Start Date (YYYY-MM-DD):",
            "End Date (YYYY-MM-DD):", "Status:"
        };

        JTextField[] fields = new JTextField[labels.length];
        JComboBox<String> leaveTypeBox = new JComboBox<>(new String[]{"Sick", "Casual", "Annual"});
        JComboBox<String> statusBox = new JComboBox<>(new String[]{"Approved", "Pending", "Rejected"});

        int y = 20;
        for (int i = 0; i < labels.length; i++) {
            JLabel label = new JLabel(labels[i]);
            label.setBounds(30, y, 200, 25);
            frame.add(label);

            if (i == 1) {
                leaveTypeBox.setBounds(230, y, 200, 25);
                frame.add(leaveTypeBox);
            } else if (i == 4) {
                statusBox.setBounds(230, y, 200, 25);
                frame.add(statusBox);
            } else {
                fields[i] = new JTextField();
                fields[i].setBounds(230, y, 200, 25);
                frame.add(fields[i]);
            }
            y += 40;
        }

        JButton insertBtn = new JButton("Insert Leave Record");
        insertBtn.setBounds(160, y + 10, 160, 30);
        frame.add(insertBtn);

        insertBtn.addActionListener(e -> {
            try (Connection con = DriverManager.getConnection(URL, USER, PWD)) {
                // Validate that all fields are filled in
                for (JTextField tf : fields) {
                    if (tf != null && tf.getText().trim().isEmpty()) {
                        JOptionPane.showMessageDialog(frame, "All fields are required!");
                        return;
                    }
                }

                // Parse the input values
                int empId = Integer.parseInt(fields[0].getText().trim());
                String leaveType = leaveTypeBox.getSelectedItem().toString();
                String status = statusBox.getSelectedItem().toString();

                LocalDate startDate = LocalDate.parse(fields[2].getText().trim(), DateTimeFormatter.ISO_LOCAL_DATE);
                LocalDate endDate = LocalDate.parse(fields[3].getText().trim(), DateTimeFormatter.ISO_LOCAL_DATE);

                // Calculate total leave days (inclusive)
                long totalDays = endDate.toEpochDay() - startDate.toEpochDay() + 1;

                String sql = "INSERT INTO `Leave` (employee_id, leave_type, leave_start_date, leave_end_date, total_leave_days, status) VALUES (?, ?, ?, ?, ?, ?)";
                try (PreparedStatement ps = con.prepareStatement(sql)) {
                    ps.setInt(1, empId);
                    ps.setString(2, leaveType);
                    ps.setDate(3, Date.valueOf(startDate));
                    ps.setDate(4, Date.valueOf(endDate));
                    ps.setLong(5, totalDays);
                    ps.setString(6, status);
                    ps.executeUpdate();
                }

                JOptionPane.showMessageDialog(frame, "Record inserted successfully!");
                for (JTextField tf : fields) if (tf != null) tf.setText("");
                leaveTypeBox.setSelectedIndex(0);
                statusBox.setSelectedIndex(0);

            } catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(frame, "Invalid Employee ID. Please enter a valid integer.");
            } catch (SQLException sqlEx) {
                JOptionPane.showMessageDialog(frame, "Database error: " + sqlEx.getMessage());
                sqlEx.printStackTrace();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Unexpected error: " + ex.getMessage());
                ex.printStackTrace();
            }
        });

        JButton viewBtn = new JButton("View Leave Records");
        viewBtn.setBounds(160, y + 50, 160, 30);
        frame.add(viewBtn);

        viewBtn.addActionListener(e -> {
            JFrame viewFrame = new JFrame("Leave Records");
            DefaultTableModel model = new DefaultTableModel(new String[]{
                 "Emp ID", "Type", "Start", "End", "Days", "Status"
            }, 0);
            JTable table = new JTable(model);
            JScrollPane pane = new JScrollPane(table);
            pane.setBounds(20, 20, 700, 300);

            try (Connection con = DriverManager.getConnection(URL, USER, PWD);
                 ResultSet rs = con.createStatement().executeQuery("SELECT * FROM `Leave`")) {
                while (rs.next()) {
                    model.addRow(new Object[]{
                        rs.getInt("employee_id"),
                        rs.getString("leave_type"),
                        rs.getDate("leave_start_date"),
                        rs.getDate("leave_end_date"),
                        rs.getInt("total_leave_days"),
                        rs.getString("status")
                    });
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(viewFrame, "Error fetching records: " + ex.getMessage());
                ex.printStackTrace();
            }

            viewFrame.add(pane);
            viewFrame.setSize(760, 400);
            viewFrame.setLayout(null);
            viewFrame.setVisible(true);
            viewFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        });

        frame.setVisible(true);
    }
}
