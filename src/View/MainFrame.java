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
	private JFrame addCatFrame;
	private JLabel addCatLabel;
	private JButton btCost;
	private JButton Category;
	private JButton ShowCategory;
	private JButton SearchCategory;
	private JButton btAddCost;
	private JButton btAddCat;
	private JTable Mtable ;
	private JTextField textField;
	private JTextField costName, costSum, costCurr, costCat, costDate;
	private JLabel costNameL, costSumL, costCurrL, costCatL, costDateL;
	private JComboBox CatList;
	private JTextField catName;
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
		Category.addActionListener(windowListener);
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
		/* ADD COST FRAME SETUP */
		addCostFrame = new JFrame("Add new cost");
		JPanel addCostPanel = new JPanel();
		BoxLayout AddCostBoxLayout = new BoxLayout(addCostPanel,BoxLayout.PAGE_AXIS);

		addCostFrame.setContentPane(addCostPanel);
		addCostFrame.getContentPane().setLayout(AddCostBoxLayout);
		addCostFrame.getContentPane().setBackground(Color.darkGray);
		textField = new JTextField(12);
//		addCostPanel.add(textField);
		addCostFrame.add(Box.createRigidArea(new Dimension(8, 20)));
		addCostFrame.setSize(512,400);
		addCostFrame.setVisible(false);
		addCostFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		/* ADD COST LABEL SETUP */
		JPanel AddCostLabelPanel = new JPanel();
		AddCostLabelPanel.setBackground(Color.darkGray);
		AddCostLabelPanel.setLayout(new BoxLayout(buttonPanel ,BoxLayout.LINE_AXIS));
		costSumL = new JLabel("Sum:");
		AddCostLabelPanel.add(costSumL);
		AddCostLabelPanel.add(Box.createRigidArea(new Dimension(8, 20)));

		costCurrL = new JLabel("Currency:");
		costCatL = new JLabel("Category:");
		costNameL = new JLabel("Description:");
		costDateL = new JLabel("Date");


////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		/* ADD COST FRAME BUTTON SETUP */
		JPanel AddCostButtonPanel = new JPanel();
		AddCostButtonPanel.setBackground(Color.darkGray);
		AddCostButtonPanel.setLayout(new BoxLayout(buttonPanel ,BoxLayout.LINE_AXIS));
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		/* ADD CATEGORY FRAME SETUP */
		addCatFrame = new JFrame("Add new category");
		JPanel addCatPanel = new JPanel();
		BoxLayout AddCatBoxLayout = new BoxLayout(addCatPanel,BoxLayout.PAGE_AXIS);
		addCatPanel.setBackground(Color.darkGray);;
		addCatFrame.setContentPane(addCatPanel);
		addCatFrame.getContentPane().setLayout(AddCatBoxLayout);
		addCatFrame.getContentPane().setBackground(Color.darkGray);
		addCatFrame.setSize(512,200);
		addCatFrame.setVisible(false);
		addCatFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		/* ADD CATEGORY TEXTFIELD SETUP */
		JPanel addCatTextPanel = new JPanel();
		addCatTextPanel.setBackground(Color.darkGray);
//		addCatTextPanel.setLayout(new BoxLayout(addCatTextPanel,BoxLayout.PAGE_AXIS));
		addCatLabel = new JLabel("New category's name:");
		addCatLabel.setForeground(Color.gray);
		catName = new JTextField(30);


		addCatTextPanel.add(Box.createRigidArea(new Dimension(5, 20)));
		addCatTextPanel.add(catName);

		addCatPanel.add(Box.createRigidArea(new Dimension(5, 40)));
		addCatPanel.add(addCatLabel);
		addCatPanel.add(addCatTextPanel);

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		/* ADD CATEGORY BUTTON SETUP */
		JPanel catButtonPanel = new JPanel();
		catButtonPanel.setBackground(Color.darkGray);
		btAddCat = new JButton("Add to Categories");
		btAddCat.addActionListener(buttonListener);
		catButtonPanel.add(btAddCat);

		addCatPanel.add(Box.createRigidArea(new Dimension(8, 20)));
		addCatPanel.add(catButtonPanel);
		addCatPanel.add(Box.createRigidArea(new Dimension(5, 20)));


////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		/* FINAL SETUP */

		this.setSize(1024, 786);
		addCatFrame.setLocationRelativeTo(null);
		this.setLocationRelativeTo(null);
		addCatFrame.setResizable(false);
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
				text = "";
			}
			else if(source==Category)
			{
				addCatFrame.setVisible(true);
				catName.setText("");
			}
			textField.setText(text);
		}
	}

}
