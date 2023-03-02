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
	private JTable table;
	private JFrame myFrame;
	private JButton BtCost;
	private JButton Category;
	private JButton ShowCategory;
	private JButton SearchCategory;
	private  JTable Mtable ;

	private ActionListener buttonListener;
	private JScrollPane sp;



	public void open_new_gui(){
		 System.out.println("open new ui ");
		 this.setLayout(new FlowLayout());
		 this.getContentPane().setBackground(Color.CYAN);
		 this.setLayout(null);
		 this.setVisible(true);
		 this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
	}
	class MyButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			Object source = e.getSource();
			switch (e.getActionCommand())
			{
				case "AddCost":
					System.out.println(e.getActionCommand());
					break;
				case "Category":
					create_table();
					break;
				case "ShowCategory":
					System.out.println(e.getActionCommand());
					break;
				case "SearchCategory":
					System.out.println(e.getActionCommand());

			}


		}

	}
	public void create_table(){
		System.out.println("enter the function create_table");
		String[] ColmNames = {"id" , "names"};
		Model.Category c1 = new Category(1,"Cloth");
		Model.Category c2 = new Category(2,"Gaz");
		String data [][] = {{c1.toString()},{c2.toString()}};
		System.out.println("we create data and Colm");
		Mtable = new JTable(data , ColmNames);
		Mtable.setBounds(30,40,200,300);
		JScrollPane sp=new JScrollPane(Mtable);
		this.add(sp);
		this.setSize(300,400);
		this.setVisible(true);







	}
	public MainFrame()
	{


		this.setLayout(new FlowLayout());
		this.getContentPane().setBackground(Color.black);
		buttonListener = new MyButtonListener();

		BtCost = new JButton("AddCost");
		BtCost.setBounds(0,10,100,30);
		BtCost.addActionListener(buttonListener);

		Category = new JButton("Category");
		Category.setBounds(90,10,100,30);
		Category.addActionListener(buttonListener);

		ShowCategory = new JButton("ShowCategory");
		ShowCategory.setBounds(180,10,150,30);
		ShowCategory.addActionListener(buttonListener);

		SearchCategory = new JButton("SearchCategory");
		SearchCategory.setBounds(300,10,150,30);
		SearchCategory.addActionListener(buttonListener);

		this.setSize(800, 800);
		this.add(BtCost);
		this.add(Category);
		this.add(ShowCategory);
		this.add(SearchCategory);
		this.setLayout(null);
		this.setVisible(true);
		System.out.println("bfa");


	    
	}
}
