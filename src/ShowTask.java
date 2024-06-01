import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class ShowTask extends JFrame {
    private DefaultTableModel model;

    public ShowTask(DefaultTableModel model) {        // have the show functionality for the project

        this.model = model;
        setTitle("Show Tasks");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 400);
        setLayout(new BorderLayout());

        JTable table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Fetch data from the database and update the table model
        fetchDataFromDatabase();

        setVisible(false);
    }

    private void fetchDataFromDatabase() {  // have the show functionality for the project
        
        String dbURL = "jdbc:mysql://localhost:3306/todo_app";
        String username = "root";
        String password = "";
        String qry_show = "SELECT * FROM todo";

        try (Connection con = DriverManager.getConnection(dbURL, username, password);
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(qry_show)) {

            // Get metadata to dynamically set table headers
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();
            String[] columnNames = new String[columnCount];
            for (int i = 1; i <= columnCount; i++) {
                columnNames[i - 1] = rsmd.getColumnName(i);
            }
            model.setColumnIdentifiers(columnNames);

            // Clear existing rows in the table model
            model.setRowCount(0);

            // Add rows from the ResultSet to the table model
            while (rs.next()) {
                Object[] rowData = new Object[columnCount];
                for (int i = 1; i <= columnCount; i++) {
                    rowData[i - 1] = rs.getObject(i);
                }
                model.addRow(rowData);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error occurred: " + ex.getMessage());
        }
    }
}
