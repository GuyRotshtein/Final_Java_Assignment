package ViewVmodel;
import Model.Category;
import Model.DBConnection;
import Model.DBConnection2;
import Model.Record;
import View.MainFrame;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ViewModel {
	private MainFrame gui;
    private DBConnection oldDB;
    private String[] TABLE_COLUMNS = {"ID","AMOUNT", "CURRENCY" ,"CATEGORY", "DESCRIPTION", "DATE"};
    private DBConnection2 derbyConnection;

    public ViewModel(String[] args){
        derbyConnection = new DBConnection2(args);

        start();
    }



    public void start(){
        insertDefaultData();
        Object data[][] = getTableData();
        gui = new MainFrame(TABLE_COLUMNS, data);


    }

    public void insertDefaultData(){
//        ArrayList<Record> migration = DBConnection.getAllRecords();
//        for(Record r : migration){
//            derbyConnection.insertRecord(r);
//        }

        ArrayList<Category> migrationCategories = DBConnection.getAllCategories();
        for(Category cat : migrationCategories){
            derbyConnection.insertCategory(cat);
        }


    }

    private Object[][] getTableData() {
        int rows = DBConnection.getDb().getAllRecords().size();
        int cols = TABLE_COLUMNS.length;

        String data[][] = new String[rows][cols];
        for(int r = 0; r < rows; r++){
            for(int c = 0; c < cols ; c++ ){
                data[r][c] = DBConnection.getDb().getAllRecords().get(r).getDataArr()[c];
            }
        }

        return (Object[][]) data;
//        return null;

    }
}
