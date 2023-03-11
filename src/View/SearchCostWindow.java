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

    public SearchCostWindow(ViewModel controller){
        viewModel = controller;
        this.setTitle("Search costs");
        JPanel contentPane = new JPanel();
        this.setContentPane(contentPane);
        BoxLayout mainContainer = new BoxLayout(contentPane, BoxLayout.PAGE_AXIS);
        this.getContentPane().setLayout(mainContainer);
        contentPane.setBackground(Color.darkGray);

//-------------------------------------------------------
        JPanel explainPanel = generateFieldPanel();
        JLabel explainLbl = generateLabel("Input year and month, and select search method");
        explainPanel.add(explainLbl);
        contentPane.add(explainPanel);
//-------------------------------------------------------

        JPanel yearPanel = generateFieldPanel();
        JLabel yearLbl = generateLabel("Year");
        JTextField yearText = new JTextField(20);
        yearPanel.add(yearLbl);
        yearPanel.add(yearText);
        contentPane.add(yearPanel);
//-------------------------------------------------------
        JPanel monthPanel = generateFieldPanel();
        JLabel monthLbl = generateLabel("month");
        JTextField monthText = new JTextField(20);
        monthPanel.add(monthLbl);
        monthPanel.add(monthText);
        contentPane.add(monthPanel);

//-------------------------------------------------------

        JPanel searchYearPanel = generateFieldPanel();
        JButton yearBtn = new JButton("Search By year");


        yearBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try{
                    updateTableModel(Integer.parseInt(yearText.getText()));
                } catch (NumberFormatException numE){
                }

            }
        });

        searchYearPanel.add(yearBtn);
        this.getContentPane().add(searchYearPanel);


//-------------------------------------------------------

        JPanel searchMonthPanel = generateFieldPanel();
        JButton monthBtn = new JButton("Search By year and month");

        monthBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try{
                updateTableModel(Integer.parseInt(yearText.getText()),Integer.parseInt(monthText.getText()));
                } catch (NumberFormatException numE){
                }
            }
        });
        searchMonthPanel.add(monthBtn);
        this.getContentPane().add(searchMonthPanel);

//-------------------------------------------------------

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

        this.setSize(512,600);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);

    }

    public JPanel generateFieldPanel(){
        JPanel result = new JPanel();
        BoxLayout panelLayout = new BoxLayout(result, BoxLayout.X_AXIS);
        result.setBackground(Color.DARK_GRAY);
        return result;
    }

    public JLabel generateLabel(String text){
        JLabel result = new JLabel(text);
        result.setForeground(Color.WHITE);
        return result;
    }

    public void updateTableModel() {
        tableModel = new DefaultTableModel(viewModel.getTableData(), viewModel.getTableColumns());
        costTable.setModel(tableModel);
        repaint();
        revalidate();
    }

    public void updateTableModel(int year) {
        tableModel = new DefaultTableModel(viewModel.searchTableData(year), viewModel.getTableColumns());
        costTable.setModel(tableModel);
        repaint();
        revalidate();
    }
    public void updateTableModel(int year, int month) {
        tableModel = new DefaultTableModel(viewModel.searchTableData(year,month), viewModel.getTableColumns());
        costTable.setModel(tableModel);
        repaint();
        revalidate();
    }

}

