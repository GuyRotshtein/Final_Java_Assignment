package View;

import Model.Category;
import Model.Cost;
import ViewVmodel.ViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.util.Arrays;
/*this is the Add cost frame*/
public class AddCostWindow extends JFrame {

    /*view model obj*/
    private ViewModel viewModel;
    /* add the cost window create view model who will contact the database and exchange the data */
    public AddCostWindow(ViewModel controller){
        viewModel = controller;
        this.setTitle("Add new cost");
        JPanel contentPane = new JPanel();
        this.setContentPane(contentPane);
        BoxLayout mainContainer = new BoxLayout(contentPane, BoxLayout.PAGE_AXIS);
        this.getContentPane().setLayout(mainContainer);
        contentPane.setBackground(Color.darkGray);

//-------------------------------------------------------
        JPanel sumPanel = generateFieldPanel();
        JLabel sumLbl = generateLabel("Sum");
        JTextField sumText = new JTextField(20);
        sumPanel.add(sumLbl);
        sumPanel.add(sumText);
        contentPane.add(sumPanel);
//-------------------------------------------------------
        /*category add label in the add cost panel*/
        JPanel categoryPanel = generateFieldPanel();
        JLabel categoryLbl = generateLabel("Category");
        String[] categories = viewModel.getCategoriesArr();
        JComboBox<String> categorySelect = new JComboBox(categories);

        categorySelect.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Get the selected item from the dropdown
                String selectedItem = (String) categorySelect.getSelectedItem();
                System.out.println(selectedItem);

            }
        });

        categoryPanel.add(categoryLbl);
        categoryPanel.add(categorySelect);
        contentPane.add(categoryPanel);
//-------------------------------------------------------
        /*Currency add label in the add cost panel*/
        JPanel currencyPanel = generateFieldPanel();
        JLabel currencyLbl = generateLabel("Currency");
        JTextField currencyText = new JTextField(20);
        currencyPanel.add(currencyLbl);
        currencyPanel.add(currencyText);
        contentPane.add(currencyPanel);
//-------------------------------------------------------
        /*Description add label in the add cost panel*/
        JPanel descriptionPanel = generateFieldPanel();
        JLabel descriptionLbl = generateLabel("Description");
        JTextField descriptionText = new JTextField(20);
        descriptionPanel.add(descriptionLbl);
        descriptionPanel.add(descriptionText);
        contentPane.add(descriptionPanel);
//-------------------------------------------------------
        /*Date add label in the add cost panel*/
        JPanel datePanel = generateFieldPanel();
        JLabel dateLbl = generateLabel("Date");
        JPanel dateInputsPanel = generateFieldPanel();
        JLabel dayLbl = generateLabel("Day");
        JTextField dayText = new JTextField(5);
        dateInputsPanel.add(dayLbl);
        dateInputsPanel.add(dayText);

        JLabel monthLbl = generateLabel("Month");
        JTextField monthText = new JTextField(5);
        dateInputsPanel.add(monthLbl);
        dateInputsPanel.add(monthText);

        JLabel yearLbl = generateLabel("Year");
        JTextField yearText = new JTextField(5);
        dateInputsPanel.add(yearLbl);
        dateInputsPanel.add(yearText);
        datePanel.add(dateLbl);
        datePanel.add(dateInputsPanel);
        this.getContentPane().add(datePanel);
//-------------------------------------------------------
    /* the add cost button */
        JButton addBtn = new JButton("Add Cost");
        this.getContentPane().add(addBtn);
        addBtn.addActionListener(new ActionListener() {
            /*deducted the  button was click*/
            public void actionPerformed(ActionEvent e) {
                double sum = Double.parseDouble(sumText.getText());
                String currency = currencyText.getText();
                String categoryName = (String) categorySelect.getSelectedItem();
                String description = descriptionText.getText();
                Date date = new Date(

                        Integer.parseInt(yearText.getText())-1900,
                        Integer.parseInt(monthText.getText())-1,
                        Integer.parseInt(dayText.getText())
                );
                viewModel.createCost(sum, currency, categoryName,description, date );
                dispose();

            }
        });
        /*the size of the frame*/
        this.setSize(512,400);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);


    }
    /*generate the field panel */
    public JPanel generateFieldPanel(){
        JPanel result = new JPanel();
        BoxLayout panelLayout = new BoxLayout(result, BoxLayout.X_AXIS);
        result.setBackground(Color.DARK_GRAY);
        return result;
    }
    /*generate the field text */
    public JLabel generateLabel(String text){
        JLabel result = new JLabel(text);
        result.setForeground(Color.WHITE);
        return result;
    }


}
