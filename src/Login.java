import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.*;

class Login extends JFrame {
    private static final long serialVersionUID = 1L;
    private JTextField usernameField;
    private JPasswordField passwordField; 
    private JButton submitButton;

    public Login() {                  // includes the login implementation 
    	
        setTitle("Login Form");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);

        JLabel userLabel = new JLabel("Enter your username");
        usernameField = new JTextField(20);

        JLabel passLabel = new JLabel("Enter your password");
        passwordField = new JPasswordField(20);  

        submitButton = new JButton("Submit");

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(3, 2));
        formPanel.add(userLabel);
        formPanel.add(usernameField);
        formPanel.add(passLabel);
        formPanel.add(passwordField);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(submitButton);

        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String dbURL = "jdbc:mysql://localhost:3306/Todo_app";
                String dbUsername = "root";
                String dbPassword = "";
                String desiredUsername = usernameField.getText();
                String desiredPassword = new String(passwordField.getPassword());  

                try (Connection con = DriverManager.getConnection(dbURL, dbUsername, dbPassword)) {
                    if (verifyCredentials(con, desiredUsername, desiredPassword)) {
                        // Login successful
                        JOptionPane.showMessageDialog(null, "Login successful!");
                        dispose();
                        MainUI n1 = new MainUI();
                        
                    } else {
                        // Invalid credentials
                        JOptionPane.showMessageDialog(null, "Invalid credentials.");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error occurred: " + ex.getMessage());
                }
            }
        });

        add(formPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    private static boolean verifyCredentials(Connection con, String username, String password) throws SQLException {
        String query = "SELECT COUNT(*) AS count FROM users WHERE username = ? AND password = ?";
        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, username);
            ps.setString(2, password);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("count") > 0;
                }
            }
        }
        return false;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Login());
    }
}
