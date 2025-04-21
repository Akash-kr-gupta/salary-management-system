package day1;

import javax.swing.*;
import java.awt.*;

public class FrontPage {

    public static void main(String[] args) {
        // Create frame
        JFrame frame = new JFrame("Employee Management System");
        frame.setSize(400, 300);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null); // Center the frame

        // Title Label
        JLabel titleLabel = new JLabel("Employee Management System");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setBounds(40, 50, 320, 40);
        frame.add(titleLabel);

        // LET'S GO Button
        JButton letsGoBtn = new JButton("LET'S GO");
        letsGoBtn.setFont(new Font("Arial", Font.BOLD, 16));
        letsGoBtn.setBounds(130, 150, 120, 40);
        frame.add(letsGoBtn);

        // Button Action: Open Dashboard
        letsGoBtn.addActionListener(e -> {
            frame.dispose(); // Close front page
            Dashboard.main(null); // Open dashboard
        });

        // Make it visible
        frame.setVisible(true);
    }
}
