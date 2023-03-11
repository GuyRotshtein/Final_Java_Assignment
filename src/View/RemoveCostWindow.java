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


public class RemoveCostWindow extends JFrame {

    private ViewModel viewModel;

    public RemoveCostWindow(ViewModel controller) {
        viewModel = controller;
        this.setTitle("Remove Expense");
        JPanel contentPane = new JPanel();
        this.setContentPane(contentPane);
        BoxLayout mainContainer = new BoxLayout(contentPane, BoxLayout.PAGE_AXIS);
        this.getContentPane().setLayout(mainContainer);
        contentPane.setBackground(Color.darkGray);

//-------------------------------------------------------
        JPanel explainPanel = generateFieldPanel();
        JLabel explainLbl = generateLabel("Input the ID of the expense");
        explainPanel.add(explainLbl);
        contentPane.add(explainPanel);
//-------------------------------------------------------

        JPanel idPanel = generateFieldPanel();
        JTextField idText = new JTextField(20);
        idPanel.add(idText);
        contentPane.add(idPanel);

//-------------------------------------------------------

        JPanel RemoveCostPanel = generateFieldPanel();
        JButton removeBtn = new JButton("Remove Expense");

        removeBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try{
                    viewModel.deleteCost(idText.getText());
                } catch (NumberFormatException numE){
                }
            }
        });
        RemoveCostPanel.add(removeBtn);
        this.getContentPane().add(RemoveCostPanel);

        this.setSize(512,200);
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
}
