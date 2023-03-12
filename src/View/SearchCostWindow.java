package View;

import Model.Category;
import Model.Cost;
import ViewVmodel.ViewModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.util.Arrays;

public class SearchCostWindow extends JFrame{

    private ViewModel viewModel;
    private JTable costTable;
    private DefaultTableModel tableModel;

    /**
     * Constructor for the SearchCost window. gets the ViewModel controller through the buttonlistener and View
     * @param controller - ViewModel controller, passed through view
     */
    public SearchCostWindow(ViewModel controller){
        viewModel = controller;
        this.setTitle("Search costs");
        JPanel contentPane = new JPanel();
        this.setContentPane(contentPane);
        BoxLayout mainContainer = new BoxLayout(contentPane, BoxLayout.PAGE_AXIS);
        this.getContentPane().setLayout(mainContainer);
        contentPane.setBackground(Color.darkGray);

//-------------------------------------------------------
        // User instructions panel
        JPanel explainPanel = generateFieldPanel();
        JLabel explainLbl = generateLabel("Input year and month, and select search method");
        explainPanel.add(explainLbl);
        contentPane.add(explainPanel);
//-------------------------------------------------------
        // Year text panel, includes text field and label
        JPanel yearPanel = generateFieldPanel();
        JLabel yearLbl = generateLabel("Year");
        JTextField yearText = new JTextField(20);
        yearPanel.add(yearLbl);
        yearPanel.add(yearText);
        contentPane.add(yearPanel);
//-------------------------------------------------------
        // Month text panel, includes text field and label
        JPanel monthPanel = generateFieldPanel();
        JLabel monthLbl = generateLabel("month");
        JTextField monthText = new JTextField(20);
        monthPanel.add(monthLbl);
        monthPanel.add(monthText);
        contentPane.add(monthPanel);

//-------------------------------------------------------
        // Search by year button panel, includes button and actionListener
        JPanel searchYearPanel = generateFieldPanel();
        JButton yearBtn = new JButton("Search By year");


        yearBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try{
                    // sends the text in the Year field to the updateTableModel function
                    updateTableModel(Integer.parseInt(yearText.getText()));
                } catch (NumberFormatException numE){
                }

            }
        });
        searchYearPanel.add(yearBtn);
        this.getContentPane().add(searchYearPanel);


//-------------------------------------------------------
        // Search by year and month button panel, includes button and actionListener
        JPanel searchMonthPanel = generateFieldPanel();
        JButton monthBtn = new JButton("Search By year and month");

        monthBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try{
                    // sends the text in the Year and Month field to the updateTableModel function
                updateTableModel(Integer.parseInt(yearText.getText()),Integer.parseInt(monthText.getText()));
                } catch (NumberFormatException numE){
                }
            }
        });
        searchMonthPanel.add(monthBtn);
        this.getContentPane().add(searchMonthPanel);

//-------------------------------------------------------
        // Results table, displaying all received results form the database
        JPanel tablePanel = generateFieldPanel();
        tablePanel.setBackground(Color.darkGray);;
        tablePanel.setSize(new Dimension(800, 600));
        Object[][] tableData = viewModel.getTableData();
        String tableColumns[] = viewModel.getTableColumns();
        costTable = new JTable(tableData.length+1, tableColumns.length);
        updateTableModel();
        costTable.setGridColor(Color.GRAY);
        costTable.setFont(new Font("Arial",Font.BOLD, 14));
        JScrollPane scrollPane = new JScrollPane(costTable);
        scrollPane.setBounds(112, 0, 800, 300);
        tablePanel.add(scrollPane);
        this.add(tablePanel);
        this.add(Box.createRigidArea(new Dimension(5, 20)));

        // Final setup and variables
        this.setSize(512,600);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);

    }

    /**
     * Generates a new panel with default variables, and returns it.
     * Simplifies creation of JPanels.
     * @return - returns created JPanel instance.
     */
    public JPanel generateFieldPanel(){
        JPanel result = new JPanel();
        BoxLayout panelLayout = new BoxLayout(result, BoxLayout.X_AXIS);
        result.setBackground(Color.DARK_GRAY);
        return result;
    }
    /**
     * Generates a new label with default variables, fills it with a given string, and returns it.
     * Simplifies creation of JLabels, and saves code.
     * @param text - string of text to be included in the label.
     * @return - returns created JLabel instance.
     */
    public JLabel generateLabel(String text){
        JLabel result = new JLabel(text);
        result.setForeground(Color.WHITE);
        return result;
    }

    /**
     * Updates the window's table model to display all given records from the database.
     */
    public void updateTableModel() {
        tableModel = new DefaultTableModel(viewModel.getTableData(), viewModel.getTableColumns());
        costTable.setModel(tableModel);
        repaint();
        revalidate();
    }

    /**
     * Updates the window's table with data from ViewModel of all records with certain years in them.
     * @param year - year that all returned cost should be within.
     */
    public void updateTableModel(int year) {
        tableModel = new DefaultTableModel(viewModel.searchTableData(year), viewModel.getTableColumns());
        costTable.setModel(tableModel);
        repaint();
        revalidate();
    }

    /**
     * Updates the window's table with data from ViewModel of all records with certain years and months in them.
     * @param year- year that all returned cost should be within.
     * @param month- month that all returned cost should be within.
     */
    public void updateTableModel(int year, int month) {
        tableModel = new DefaultTableModel(viewModel.searchTableData(year,month), viewModel.getTableColumns());
        costTable.setModel(tableModel);
        repaint();
        revalidate();
    }

}

