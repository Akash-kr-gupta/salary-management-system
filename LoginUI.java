package day1;

import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class LoginUI {

    static final String URL = "jdbc:mysql://localhost:3306/student";
    static final String USER = "root";
    static final String PWD = "abc123";

    public static void main(String[] args) {
        JFrame loginFrame = new JFrame("Login");

        JLabel l1 = new JLabel("Username:");
        JLabel l2 = new JLabel("Password:");

        JTextField t1 = new JTextField();
        JPasswordField t2 = new JPasswordField();

        JButton loginBtn = new JButton("Login");

        l1.setBounds(30, 30, 100, 30);
        l2.setBounds(30, 70, 100, 30);
        t1.setBounds(150, 30, 150, 30);
        t2.setBounds(150, 70, 150, 30);
        loginBtn.setBounds(100, 120, 100, 30);

        loginFrame.add(l1); loginFrame.add(t1);
        loginFrame.add(l2); loginFrame.add(t2);
        loginFrame.add(loginBtn);

        loginFrame.setSize(350, 220);
        loginFrame.setLayout(null);
        loginFrame.setVisible(true);
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        loginBtn.addActionListener(e -> {
            String username = t1.getText();
            String password = new String(t2.getPassword());

            try (Connection con = DriverManager.getConnection(URL, USER, PWD)) {
                String query = "SELECT * FROM login WHERE username = ? AND password = ?";
                PreparedStatement ps = con.prepareStatement(query);
                ps.setString(1, username);
                ps.setString(2, password);

                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    JOptionPane.showMessageDialog(loginFrame, "Login Successful!");
                    loginFrame.dispose(); // Close login window
                    SalaryUI.main(null); // Launch salary UI
                } else {
                    JOptionPane.showMessageDialog(loginFrame, "Invalid credentials.");
                }

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(loginFrame, "Error: " + ex.getMessage());
            }
        });
    }
}
