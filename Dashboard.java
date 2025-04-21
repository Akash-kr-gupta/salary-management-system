package day1;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class Dashboard {

    static final String URL = "jdbc:mysql://localhost:3306/student";
    static final String USER = "root";
    static final String PWD = "abc123";

    public static void main(String[] args) {
        // Optional: Set Look and Feel
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ex) {
            System.out.println("Look and feel not set: " + ex.getMessage());
        }

        // Login Frame setup
        JFrame loginFrame = new JFrame("Login");
        loginFrame.setSize(350, 220);
        loginFrame.setLayout(null);
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setLocationRelativeTo(null);

        JLabel l1 = new JLabel("Username:");
        JLabel l2 = new JLabel("Password:");
        JTextField t1 = new JTextField();
        JPasswordField t2 = new JPasswordField();
        JButton loginBtn = new JButton("Login");

        l1.setBounds(30, 30, 100, 30);
        l2.setBounds(30, 70, 100, 30);
        t1.setBounds(150, 30, 150, 30);
        t2.setBounds(150, 70, 150, 30);
        loginBtn.setBounds(120, 120, 100, 30);

        loginFrame.add(l1);
        loginFrame.add(t1);
        loginFrame.add(l2);
        loginFrame.add(t2);
        loginFrame.add(loginBtn);

        loginFrame.setVisible(true);

        loginBtn.addActionListener(e -> {
            String username = t1.getText();
            String password = new String(t2.getPassword());

            try (Connection con = DriverManager.getConnection(URL, USER, PWD)) {
                String query = "SELECT * FROM login WHERE username = ? AND password = ?";
                try (PreparedStatement ps = con.prepareStatement(query)) {
                    ps.setString(1, username);
                    ps.setString(2, password);

                    ResultSet rs = ps.executeQuery();
                    if (rs.next()) {
                        loginFrame.dispose();
                        showDashboard();
                    } else {
                        JOptionPane.showMessageDialog(loginFrame, "Invalid credentials.");
                    }
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(loginFrame, "Error: " + ex.getMessage());
            }
        });
    }

    // Show the dashboard screen
    private static void showDashboard() {
        JFrame newFrame = new JFrame("Dashboard - Employee Management System");
        newFrame.setSize(420, 670);
        newFrame.setLayout(null);
        newFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        newFrame.setLocationRelativeTo(null);

        JLabel newTitleLabel = new JLabel("Welcome to the Dashboard");
        newTitleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        newTitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        newTitleLabel.setBounds(60, 20, 300, 30);
        newFrame.add(newTitleLabel);

        JLabel subLabel = new JLabel("Choose an action below:");
        subLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        subLabel.setHorizontalAlignment(SwingConstants.CENTER);
        subLabel.setBounds(100, 50, 220, 20);
        newFrame.add(subLabel);

        // Dashboard Buttons
        JButton empInsertBtn = new JButton("Insert Employee");
        JButton accountBtn = new JButton("Insert Account");
        JButton attendanceBtn = new JButton("Mark Attendance");
        JButton salaryBtn = new JButton("Calculate Salary");
        JButton employeeListBtn = new JButton("View Employee List");
        JButton leaveBtn = new JButton("Manage Leave");
        JButton deptInsertBtn = new JButton("Insert Department");
        JButton deptListBtn = new JButton("View Departments");
        JButton bankDetailsBtn = new JButton("Insert Bank Details");
        JButton payrollBtn = new JButton("Insert Payroll Details");
        JButton taxInsertBtn = new JButton("Insert Tax Record");
        JButton exitBtn = new JButton("Exit");

        int y = 90;
        int height = 35;
        int spacing = 45;

        JButton[] buttons = {
            empInsertBtn, accountBtn, attendanceBtn, salaryBtn, employeeListBtn,
            leaveBtn, deptInsertBtn, deptListBtn, bankDetailsBtn, payrollBtn,
            taxInsertBtn, exitBtn
        };

        for (JButton btn : buttons) {
            btn.setBounds(110, y, 200, height);
            newFrame.add(btn);
            y += spacing;
        }

        // Button actions
        empInsertBtn.addActionListener(e -> EmployeeInsertUI.main(null));
        accountBtn.addActionListener(e -> InsertAccountUI.main(null));
        attendanceBtn.addActionListener(e -> AttendanceUI.main(null));
        salaryBtn.addActionListener(e -> SalaryUI.main(null));
        employeeListBtn.addActionListener(e -> EmployeeListUI.main(null));
        leaveBtn.addActionListener(e -> LeaveManagementUI.main(null));
        deptInsertBtn.addActionListener(e -> DepartmentInsertUI.main(null));
        deptListBtn.addActionListener(e -> DepartmentListUI.main(null));
        bankDetailsBtn.addActionListener(e -> BankDetailsUI.main(null));
        payrollBtn.addActionListener(e -> PayrollInsertUI.main(null));
        taxInsertBtn.addActionListener(e -> TaxInsertUI.main(null));

        exitBtn.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(newFrame, "Are you sure you want to exit?", "Exit Confirmation", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });

        newFrame.setVisible(true);
    }
}
