import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class DeleteTask extends JFrame {     // have the delete functionality for the project

    private DefaultTableModel model;
    private JTable table;

    public DeleteTask(DefaultTableModel model, JTable table) {
        this.model = model;
        this.table = table;
        setTitle("Delete Task");
        setSize(400, 200);
        setLayout(new FlowLayout());

  
        JButton deleteButton = new JButton("Delete");

        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    int confirmDelete = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this task?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
                    if (confirmDelete == JOptionPane.YES_OPTION) {
                        deleteSelectedRow(selectedRow);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please select a row to delete.");
                }
            }
        });

     
        
        add(deleteButton);

        setVisible(true); // Make the frame visible

        // Center the frame on the screen
        setLocationRelativeTo(null);
    }

    private void deleteSelectedRow(int selectedRow) {
        int taskId = (int) model.getValueAt(selectedRow, 0); // Assuming the first column is the task ID
        String dbURL = "jdbc:mysql://localhost:3306/todo_app";
        String username = "root";
        String password = "";
        String qry_delete = "DELETE FROM todo WHERE id = ?";

        try (Connection con = DriverManager.getConnection(dbURL, username, password);
             PreparedStatement statement = con.prepareStatement(qry_delete)) {

            statement.setInt(1, taskId);
            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                model.removeRow(selectedRow);
                JOptionPane.showMessageDialog(null, "Task deleted successfully!");
            } else {
                JOptionPane.showMessageDialog(null, "No task found with ID: " + taskId);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error occurred: " + ex.getMessage());
        }
    }
}
