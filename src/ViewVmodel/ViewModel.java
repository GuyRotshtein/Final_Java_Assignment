package ViewVmodel;

import Model.DBConnection;
import View.MainFrame;

public class ViewModel {
	private MainFrame gui;
    private DBConnection dbConnection;
    private String[] TABLE_COLUMNS = {"ID","AMOUNT", "CURRENCY" ,"CATEGORY", "DESCRIPTION", "DATE"};

    public ViewModel(){
        start();
    }



    public void start(){
        DBConnection.connectDB();
        Object data[][] = getTableData();
        gui = new MainFrame(TABLE_COLUMNS, data);

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
