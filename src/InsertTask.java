import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class InsertTask extends JFrame {        // have the insert functionality for the project
    private DefaultTableModel model;

    public InsertTask(DefaultTableModel model) {
        this.model = model;
        setTitle("Insert Task");
        setSize(400, 200);
        setLayout(new FlowLayout());

        JLabel taskLabel = new JLabel("Task:");
        JTextField taskField = new JTextField(20);
        JLabel statusLabel = new JLabel("Status:");
        JTextField statusField = new JTextField(20);
        JButton submitButton = new JButton("Submit");

        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String dbURL = "jdbc:mysql://localhost:3306/Todo_app";
                String dbUsername = "root";
                String dbPassword = "";

                try (Connection con = DriverManager.getConnection(dbURL, dbUsername, dbPassword)) {
                    String qry_insert = "INSERT INTO todo (tasks, status) VALUES (?, ?)";
                    try (PreparedStatement statement = con.prepareStatement(qry_insert)) {
                        statement.setString(1, taskField.getText());
                        statement.setString(2, statusField.getText());
                        int rowsInserted = statement.executeUpdate();
                        if (rowsInserted > 0) {
                            JOptionPane.showMessageDialog(null, "Inserted Records");
                            // Clearing the text fields
                            taskField.setText("");
                            statusField.setText("");
                        }
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error occurred: " + ex.getMessage());
                }
            }
        });
        add(taskLabel);
        add(taskField);
        add(statusLabel);
        add(statusField);
        add(submitButton);

        setVisible(true);
    }
}
