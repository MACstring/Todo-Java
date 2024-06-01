import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.table.DefaultTableModel;


public class main {                             // have the UI implementation for the whole project 
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Login();
        });
    }
}


    
 class MainUI extends JFrame {
    private JButton insertButton;
    private JButton deleteButton;
    private JButton showButton;
    private JButton updateButton;
    private DefaultTableModel model;
    private JTable table;
    public MainUI() {
        setTitle("Task Manager");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLayout(new BorderLayout());

        insertButton = new JButton("Insert Task");
        deleteButton = new JButton("Delete Task");
        showButton = new JButton("Show Tasks");
        updateButton = new JButton("Update Task"); // Initialize Update button

        model = new DefaultTableModel();
        table = new JTable(model);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(insertButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(showButton);
        buttonPanel.add(updateButton); // Add Update button to the button panel

        // Pass the JTable reference to the DeleteTask constructor
        deleteButton.addActionListener(e -> new DeleteTask(model, table));
        insertButton.addActionListener(e -> new InsertTask(model));
        showButton.addActionListener(e -> new ShowTask(model));
        updateButton.addActionListener(e -> new UpdateTask(model, table)); // Add ActionListener to Update button

        add(new JScrollPane(table), BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainUI::new);
    }
}