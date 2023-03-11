package View;
import Model.Category;
import ViewVmodel.ViewModel;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.*;
import javax.swing.JScrollPane;
import java.awt.*;
import java.awt.event.*;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTable;
import java.awt.Dimension;
import javax.swing.table.DefaultTableModel;


public class MainFrame extends JFrame {
	/**
	 * 
	 */
	ViewModel viewmodel;
	private static final long serialVersionUID = 1L;
	private JTable costTable;
	private JFrame addCostFrame;
	private JFrame addCatFrame;
	private JLabel addCatLabel;
	private JButton btCost;
	private JButton Category;
	private JButton ShowCategory;
	private JButton SearchCosts;
	private JButton Add;
	private JButton Add_catagory;
	private JButton btAddCost;
	private JButton btAddCat;
	private JTable Mtable ;
	private JTextField textField;
	private JTextField costId, costName, costSum, costCurr, costCat, costDate;
	private JTextField CatName;
	private ActionListener buttonListener;
	private OpenWindowListener windowListener;
	private JScrollPane sp;

	private DefaultTableModel tableModel;

	public MainFrame(ViewModel controller)
	{
		viewmodel = controller;
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		/* CONTENT PANE SETUP */
		JPanel contentPanel = new JPanel();
		BoxLayout pageLayout = new BoxLayout(contentPanel, BoxLayout.PAGE_AXIS);
		this.setContentPane(contentPanel);
		this.getContentPane().setLayout(pageLayout);
		this.getContentPane().setBackground(Color.darkGray);
		windowListener = new OpenWindowListener();
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		/* BUTTON SETUP */

		JPanel buttonPanel = new JPanel();
		buttonPanel.setBackground(Color.darkGray);
		buttonPanel.setLayout(new BoxLayout(buttonPanel ,BoxLayout.LINE_AXIS));

		btCost = new JButton("Add Cost");
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



		SearchCosts = new JButton("Search Expenses");
		SearchCosts.addActionListener(windowListener);
		buttonPanel.add(SearchCosts);
		this.add(Box.createRigidArea(new Dimension(8, 20)));
		this.add(buttonPanel);
		this.add(Box.createRigidArea(new Dimension(5, 20)));

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		/* TABLE SETUP */
		JPanel tablePanel = new JPanel(null);
		tablePanel.setBackground(Color.darkGray);;
		tablePanel.setSize(new Dimension(800, 600));
		Object[][] tableData = viewmodel.getTableData();
		String tableColumns[] = viewmodel.getTableColumns();
		costTable = new JTable(tableData.length+1, tableColumns.length);
		updateTableModel();
		costTable.setGridColor(Color.GRAY);
		costTable.setFont(new Font("Arial",Font.BOLD, 14));
		JScrollPane scrollPane = new JScrollPane(costTable);
		scrollPane.setBounds(112, 0, 800, 300);
		tablePanel.add(scrollPane);
		this.add(tablePanel);
		this.add(Box.createRigidArea(new Dimension(5, 20)));

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
		CatName = new JTextField(30);


		addCatTextPanel.add(Box.createRigidArea(new Dimension(5, 20)));
		addCatTextPanel.add(CatName);

		addCatPanel.add(Box.createRigidArea(new Dimension(5, 40)));
		addCatPanel.add(addCatLabel);
		addCatPanel.add(addCatTextPanel);

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		/* ADD CATEGORY BUTTON SETUP */
		JPanel catButtonPanel = new JPanel();
		catButtonPanel.setBackground(Color.darkGray);
		btAddCat = new JButton("Add to Categories");
		btAddCat.addActionListener(windowListener);
		catButtonPanel.add(btAddCat);

		addCatPanel.add(Box.createRigidArea(new Dimension(8, 20)));
		addCatPanel.add(catButtonPanel);
		addCatPanel.add(Box.createRigidArea(new Dimension(5, 20)));

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		/* ADD Delete Edit */
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		/* FINAL SETUP */

		this.setSize(1024, 786);
		addCatFrame.setLocationRelativeTo(null);
		this.setLocationRelativeTo(null);
		addCatFrame.setResizable(false);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void updateTableModel() {
		tableModel = new DefaultTableModel(viewmodel.getTableData(), viewmodel.getTableColumns());
		costTable.setModel(tableModel);
		repaint();
		revalidate();
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
				AddCostWindow addCostWindow = new AddCostWindow(viewmodel);
			}
			else if(source==Category)
			{
				addCatFrame.setVisible(true);
				CatName.setText("");
				System.out.println("catname is " + CatName.getText());
				System.out.println("source is " + e.getActionCommand());
				source = e.getSource();

			}
			else if(source==btAddCat)
			{
				System.out.println("trilili trala");
				System.out.println("source is " + e.getActionCommand());
				System.out.println("the text is " + CatName.getText());
				viewmodel.AddCategory(CatName.getText());
			}
			else if(source==SearchCosts)
			{
				SearchCostWindow searchCostWindow = new SearchCostWindow(viewmodel);

			}
//			textField.setText(text);
		}
	}

}
