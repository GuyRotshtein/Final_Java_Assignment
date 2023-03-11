package ViewVmodel;

import Model.*;

import Model.Cost;
import View.AddCostWindow;
import View.MainFrame;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;



public class ViewModel  extends Thread
{
	private MainFrame gui;
    private DBConnection oldDB;
    private String[] TABLE_COLUMNS = {"ID","AMOUNT", "CURRENCY" ,"CATEGORY", "DESCRIPTION", "DATE"};
    private DerbyConnection derbyConnection;
    private String[][] tableData;


    public ViewModel(){
        DBConnection.connectDB();
        derbyConnection = new DerbyConnection();
        start();

    }

    public void start(){
//        insertDefaultData();
        updateTableData();
        gui = new MainFrame(this);
    }

    public void insertDefaultData(){

        ArrayList<Category> migrationCategories = oldDB.getAllCategories();
        for(Category cat : migrationCategories){
            derbyConnection.insertCategory(cat);
        }

        ArrayList<Cost> migration = DBConnection.getAllCost();
        for(Cost c : migration){
            derbyConnection.insertCost(c);
        }
    }

    public void printTableData(){
        for(int i = 0; i < tableData.length; i++){
            for(int j = 0; j < tableData[i].length; j++){
                System.out.print(tableData[i][j] + "\t");
            }
            System.out.println();
        }
    }


    private void updateTableData(){
        ArrayList<Cost> allCosts = derbyConnection.getAllCosts();
//        System.out.println(allCosts.size());
        int rows = allCosts.size();
        int cols = allCosts.get(0).getDataArr().length;
        tableData = new String[rows][cols];
        for(int r = 0; r < rows; r++){
            tableData[r] = allCosts.get(r).getDataArr();
        }
        if(gui != null) gui.updateTableModel();

    }

    public Object[][] getTableData()
    {
        return (Object[][]) tableData;
    }

    public void AddCategory(String name){
        Category newCategory = new Category(name);
        derbyConnection.insertCategory(newCategory);
    }


    public String[] getTableColumns() {
        return TABLE_COLUMNS;
    }

    public String[] getCategoriesArr() {
        ArrayList<Category> allCategories = derbyConnection.getAllCategories();
        String[] result = new String[allCategories.size()];
        for(int i = 0; i < allCategories.size(); i++){
            result[i] = allCategories.get(i).getName();
        }
        return result;
    }

    public void createCost(double sum, String currency, String categoryName, String description, Date date) {
        Cost newCost = new Cost(sum, currency,new Category(categoryName), description, date);
        derbyConnection.insertCost(newCost);
        updateTableData();
    }

    public String[][] searchTableData(int year){
        ArrayList<Cost> allCosts = derbyConnection.searchCosts(year);
        int rows = allCosts.size();
        if (allCosts.get(0).getDataArr().length == 0)
        {
            tableData = new String[rows][0];
        }
        else {
            int cols = allCosts.get(0).getDataArr().length;
            tableData = new String[rows][cols];
        }
        for(int r = 0; r < rows; r++){
            tableData[r] = allCosts.get(r).getDataArr();
        }
        return tableData;
    }
    public String[][] searchTableData(int year,int month){
        ArrayList<Cost> allCosts = derbyConnection.searchCosts(year,month);
        int rows = allCosts.size();
        if (allCosts.get(0).getDataArr().length == 0)
        {
            tableData = new String[rows][0];
        }
        else {
            int cols = allCosts.get(0).getDataArr().length;
            tableData = new String[rows][cols];
        }
        for(int r = 0; r < rows; r++){
            tableData[r] = allCosts.get(r).getDataArr();
        }
        return tableData;
    }

    public void deleteCost(String costId){
        int id = Integer.parseInt(costId);
        derbyConnection.removeCost(id);
        updateTableData();
    }
}
