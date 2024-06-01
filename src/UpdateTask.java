import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class UpdateTask extends JFrame {
    private DefaultTableModel model;
    private JTable table;

    public UpdateTask(DefaultTableModel model, JTable table) { // have the update functionality for the project
        this.model = model;
        this.table = table;
        setTitle("Update Task");
        setSize(400, 200);
        setLayout(new FlowLayout());

     
        JButton updateButton = new JButton("Update");

        updateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    int taskId = (int) model.getValueAt(selectedRow, 0); // Assuming the first column is the task ID
                    String newTask = JOptionPane.showInputDialog(null, "Enter new task:");
                    String newStatus = JOptionPane.showInputDialog(null, "Enter new status:");

                    updateSelectedRow(taskId, newTask, newStatus, selectedRow);
                } else {
                    JOptionPane.showMessageDialog(null, "Please select a row to update.");
                }
            }
        });
		
        add(updateButton);

        setVisible(true);
    }

    private void updateSelectedRow(int taskId, String newTask, String newStatus, int selectedRow) {
        String dbURL = "jdbc:mysql://localhost:3306/todo_app";
        String username = "root";
        String password = "";
        String qry_update = "UPDATE todo SET tasks = ?, status = ? WHERE id = ?";

        try (Connection con = DriverManager.getConnection(dbURL, username, password);
             PreparedStatement statement = con.prepareStatement(qry_update)) {

            statement.setString(1, newTask);
            statement.setString(2, newStatus);
            statement.setInt(3, taskId);

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                model.setValueAt(newTask, selectedRow, 1); // Update task in table model
                model.setValueAt(newStatus, selectedRow, 2); // Update status in table model
                JOptionPane.showMessageDialog(null, "Task updated successfully!");
            } else {
                JOptionPane.showMessageDialog(null, "No task found with ID: " + taskId);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error occurred: " + ex.getMessage());
        }
    }
}
