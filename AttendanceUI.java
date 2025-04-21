package day1;

import javax.swing.*;
import java.awt.event.*;
import java.sql.*;
import java.time.LocalDate;

public class AttendanceUI {

    static final String URL = "jdbc:mysql://localhost:3306/student";
    static final String USER = "root";
    static final String PWD = "abc123";

    public static void main(String[] args) {
        // Create the frame for marking attendance
        JFrame frame = new JFrame("Mark Attendance");

        JLabel l1 = new JLabel("Employee ID:");
        JLabel l2 = new JLabel("Status (Present/Absent):");

        JTextField t1 = new JTextField(); // Employee ID
        JTextField t2 = new JTextField(); // Status (Present/Absent)

        JButton markBtn = new JButton("Mark Attendance");

        JLabel totalPresentLabel = new JLabel("Total Present: 0");
        JLabel totalAbsentLabel = new JLabel("Total Absent: 0");

        // Set bounds
        l1.setBounds(30, 30, 150, 30);
        l2.setBounds(30, 70, 180, 30);

        t1.setBounds(210, 30, 150, 30);
        t2.setBounds(210, 70, 150, 30);

        markBtn.setBounds(120, 120, 180, 30);

        totalPresentLabel.setBounds(30, 170, 200, 30);
        totalAbsentLabel.setBounds(30, 200, 200, 30);

        // Add components
        frame.add(l1); frame.add(t1);
        frame.add(l2); frame.add(t2);
        frame.add(markBtn);
        frame.add(totalPresentLabel);
        frame.add(totalAbsentLabel);

        frame.setSize(420, 300);
        frame.setLayout(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        markBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int employeeId;
                String status = t2.getText().trim();
                Date date = Date.valueOf(LocalDate.now());

                try {
                    employeeId = Integer.parseInt(t1.getText());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Invalid Employee ID");
                    return;
                }

                try (Connection con = DriverManager.getConnection(URL, USER, PWD)) {
                    // Insert attendance record
                    String query = "INSERT INTO attendance (employee_id, date, status) VALUES (?, ?, ?)";
                    PreparedStatement ps = con.prepareStatement(query);
                    ps.setInt(1, employeeId);
                    ps.setDate(2, date);
                    ps.setString(3, status);
                    ps.executeUpdate();

                    // Count Present
                    String countQuery = "SELECT COUNT(*) FROM attendance WHERE status = 'Present'";
                    PreparedStatement countStmt = con.prepareStatement(countQuery);
                    ResultSet rsPresent = countStmt.executeQuery();
                    int totalPresent = rsPresent.next() ? rsPresent.getInt(1) : 0;

                    // Count Absent
                    countQuery = "SELECT COUNT(*) FROM attendance WHERE status = 'Absent'";
                    countStmt = con.prepareStatement(countQuery);
                    ResultSet rsAbsent = countStmt.executeQuery();
                    int totalAbsent = rsAbsent.next() ? rsAbsent.getInt(1) : 0;

                    // Update labels
                    totalPresentLabel.setText("Total Present: " + totalPresent);
                    totalAbsentLabel.setText("Total Absent: " + totalAbsent);

                    JOptionPane.showMessageDialog(frame, "Attendance marked!");
                    t1.setText("");
                    t2.setText("");
                } catch (SQLIntegrityConstraintViolationException ex) {
                    JOptionPane.showMessageDialog(frame, "Attendance for this employee is already marked for today.");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage());
                    ex.printStackTrace();
                }
            }
        });
    }
}
