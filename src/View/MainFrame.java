package View;
import Model.Category;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.*;
import javax.swing.JScrollPane;
import java.awt.*;
import java.awt.event.*;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.Border;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.util.HashMap;
import javax.swing.JTable;
import javax.swing.plaf.basic.BasicOptionPaneUI;
import javax.swing.table.DefaultTableModel;

public class MainFrame extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTable expanseTable;
	private JFrame myFrame;
	private JButton btCost;
	private JButton Category;
	private JButton ShowCategory;
	private JButton SearchCategory;
	private JTable Mtable ;

	private ActionListener buttonListener;
	private JScrollPane sp;
	public MainFrame(String[] tableColumns, Object[][] tableData)
	{
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		/* CONTENT PANE SETUP */
		JPanel contentPanel = new JPanel();
		BoxLayout pageLayout = new BoxLayout(contentPanel, BoxLayout.PAGE_AXIS);
		this.setContentPane(contentPanel);
		this.getContentPane().setLayout(pageLayout);
		this.getContentPane().setBackground(Color.darkGray);
//		buttonListener = new MyButtonListen+er();
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		/* BUTTON SETUP */

		JPanel buttonPanel = new JPanel();
		buttonPanel.setBackground(Color.darkGray);
		buttonPanel.setLayout(new BoxLayout(buttonPanel ,BoxLayout.LINE_AXIS));

		btCost = new JButton("Add Expense");
		btCost.addActionListener(buttonListener);
		buttonPanel.add(btCost);
		buttonPanel.add(Box.createRigidArea(new Dimension(8, 20)));

		Category = new JButton("Add Category");
		Category.addActionListener(buttonListener);
		buttonPanel.add(Category);
		buttonPanel.add(Box.createRigidArea(new Dimension(8, 20)));

		ShowCategory = new JButton("Show Category");
		ShowCategory.addActionListener(buttonListener);
		buttonPanel.add(ShowCategory);
		buttonPanel.add(Box.createRigidArea(new Dimension(8, 20)));

		SearchCategory = new JButton("Search Category");
		SearchCategory.addActionListener(buttonListener);
		buttonPanel.add(SearchCategory);
		this.add(Box.createRigidArea(new Dimension(8, 20)));
		this.add(buttonPanel);
		this.add(Box.createRigidArea(new Dimension(5, 20)));

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		/* TABLE SETUP */
		JPanel tablePanel = new JPanel(null);
		tablePanel.setBackground(Color.darkGray);;
		tablePanel.setSize(new Dimension(800, 600));

		expanseTable = new JTable(tableData.length+1, tableColumns.length);
		DefaultTableModel tableModel = new DefaultTableModel(tableData, tableColumns);
		expanseTable.setModel(tableModel);
		expanseTable.setGridColor(Color.GRAY);
		expanseTable.setFont(new Font("Arial",Font.BOLD, 14));
		JScrollPane scrollPane = new JScrollPane(expanseTable);
		scrollPane.setBounds(112, 0, 800, 300);
		tablePanel.add(scrollPane);
		this.add(tablePanel);
		this.add(Box.createRigidArea(new Dimension(5, 20)));
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		/* FINAL SETUP */

		this.setSize(1024, 786);

		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
