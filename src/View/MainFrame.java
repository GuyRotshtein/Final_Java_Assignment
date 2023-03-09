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
	private JFrame addCostFrame;
	private JButton btCost;
	private JButton Category;
	private JButton ShowCategory;
	private JButton SearchCategory;
	private JButton btAddCost;
	private JTable Mtable ;
	private JTextField textField;
	private JTextField costId, costName, costSum, costCurr, costCat, costDate;
	private ActionListener buttonListener;
	private OpenWindowListener windowListener;
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
		windowListener = new OpenWindowListener();
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		/* BUTTON SETUP */

		JPanel buttonPanel = new JPanel();
		buttonPanel.setBackground(Color.darkGray);
		buttonPanel.setLayout(new BoxLayout(buttonPanel ,BoxLayout.LINE_AXIS));

		btCost = new JButton("Add Expense");
		btCost.addActionListener(windowListener);
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
		/* ADD COST PANEL SETUP */
		addCostFrame = new JFrame("Add new cost");
		JPanel addCostPanel = new JPanel();
		BoxLayout ACBoxLayout = new BoxLayout(addCostPanel,BoxLayout.PAGE_AXIS);

		addCostFrame.setContentPane(addCostPanel);
		addCostFrame.getContentPane().setLayout(ACBoxLayout);
		addCostFrame.getContentPane().setBackground(Color.darkGray);
		textField = new JTextField(12);
		addCostPanel.add(textField);
		addCostFrame.add(Box.createRigidArea(new Dimension(8, 20)));
		addCostFrame.setSize(512,400);
		addCostFrame.setVisible(false);
		addCostFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		/* ADD COST FRAME BUTTON SETUP */
		JPanel ACButtonPanel = new JPanel();
		ACButtonPanel.setBackground(Color.darkGray);
		ACButtonPanel.setLayout(new BoxLayout(buttonPanel ,BoxLayout.LINE_AXIS));

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		/* FINAL SETUP */

		this.setSize(1024, 786);

		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/* ACTION LISTENER SETUP */
	class OpenWindowListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			String text = null;
			Object source = e.getSource();
			if(source==btCost)
			{
				addCostFrame.setVisible(true);
				text = "One";
			}
			else if(source==Category)
			{
				text = "Two";
			}
			textField.setText(text);
		}
	}

}
