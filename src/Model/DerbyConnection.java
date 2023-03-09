package Model;

import View.MainFrame;

import javax.swing.plaf.nimbus.State;
import java.sql.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

public class DerbyConnection {

    /*Config Strings*/
    private String driver = "org.apache.derby.jdbc.EmbeddedDriver";
    private String framework = "embedded";
    private String protocol = "jdbc:derby:";
    private String DBNAME = "derbyDB";
    private String[] TABLE_COLUMNS = {"ID","AMOUNT", "CURRENCY" ,"CATEGORY", "DESCRIPTION", "DATE"};
    ////////////////////////////////////////////////

    private ArrayList<Statement> statements;
    private Statement sqlCmd;
    private Connection conn = null;
    private PreparedStatement psCategoryInsert, psCostInsert;
    private PreparedStatement psCategoryUpdate, psCostUpdate;
    private PreparedStatement psCategoryDelete, psCostDelete;

    private MainFrame gui;

    public DerbyConnection(String[] args){
        System.out.println("Application starting in " + framework + " mode");
        createConnection();
        prepareDB();
        prepareStatements();
    }

    public void createConnection(){
        Properties props = new Properties(); // connection properties
        try {


            Class.forName(driver);
            conn = DriverManager.getConnection(protocol +  DBNAME
                    + ";create=true", props);
            System.out.println("Connected to and created database " + DBNAME);
            conn.setAutoCommit(false);

        } catch(Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

    }

    public void prepareDB(){
        try {
            statements = new ArrayList<>(); // DELETE LATER
            sqlCmd = conn.createStatement();
            statements.add(sqlCmd); // DELETE LATER
//            deleteTables(sqlCmd);
            createCategories(sqlCmd);
            createCostTable(sqlCmd);
            conn.commit();
            getAllCategories(sqlCmd);
            gui = new MainFrame(TABLE_COLUMNS, getCostData(sqlCmd));
        }
        catch (Exception e){
            System.out.println(e.hashCode());
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
    public void deleteTables(Statement s) throws Exception{
        s.execute("drop table Cost ");
        System.out.println("COST table deleted");
        s.execute("drop table Categories ");
        System.out.println("CATEGORIES table deleted");
    }

    public void createCategories(Statement s) {
        try {
            String costTableSQL = "" +
                    "CREATE TABLE Categories" +
                    "(Id INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY(Start with 1, Increment by 1), " +
                    "Name varchar(40))";
            s.execute(costTableSQL);
            System.out.println("Created table Categories");

        }
        catch(SQLException e){
            System.out.println(e.getMessage());
            if(e.getSQLState() == "X0Y32"){
                System.out.println("Tables already exist! Moving on...");
                return;
            }
        }
    }

    public void createCostTable(Statement s) {
        try{
            String createCategoriesTableSQL = "" +
                    "CREATE TABLE Cost" +
                    "(Id INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY(Start with 1, Increment by 1), " +
                    "costSum double, " +
                    "currency varchar(20), " +
                    "category INTEGER REFERENCES Categories (id) NOT NULL , " +
                    "description varchar(60), " +
                    "costDate Date)";
            s.execute(createCategoriesTableSQL);
            System.out.println("Created table Cost");
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
            if(e.getSQLState() == "X0Y32"){
                System.out.println("Tables already exist! Moving on...");
                return;
            }
        }



    }

    public void prepareStatements(){
        try {
            psCategoryInsert = conn.prepareStatement(
                    "insert into Categories (Name) values (?)");
            //psCostInsert = conn.prepareStatement(
            //        "insert into Cost values (?,?,?,?,?,?)");
            //statements.add(psCostInsert);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        statements.add(psCategoryInsert);
    }

    public void getAllCategories(Statement s) throws Exception{
        String test = "SELECT * FROM Categories";
        ResultSet results = s.executeQuery(test);
        int size = results.getFetchSize();
//        System.out.println(results.getCursorName());

    }

    public void insertCategory(Category cat){
        try{
            psCategoryInsert.setString(1, cat.getName());
            psCategoryInsert.executeUpdate();
            System.out.println("Inserted Category");
        } catch (SQLException sqle)
        {
            sqle.printStackTrace();
//            printSQLException(sqle);
        }
    }

    public void insertCost(int id, double sum, String curr, int cat, String desc, Date date){
        try{
            psCostInsert.setInt(1,id);
            psCostInsert.setDouble(2,sum);
            psCostInsert.setString(3,curr);
            psCostInsert.setInt(4,cat);
            psCostInsert.setString(5,desc);
            psCostInsert.setDate(6,date);
            psCostInsert.executeUpdate();
            System.out.println("Inserted Record");
        } catch (SQLException sqle)
        {
            sqle.printStackTrace();
        }
    }

    private Object[][] getCostData(Statement s) {
        try{
            String rowQuery = "SELECT COUNT (*) FROM Cost";
            ResultSet rs = s.executeQuery(rowQuery);
            int rows = 0;
            if(rs.next()){
                rows = (int)rs.getLong(1);
                System.out.println("We found "+ rows + " rows!!");
            }
            else{
                System.out.println("No rows found!!!");
                return null;
            }
            String query = "SELECT * FROM Cost";
            rs = s.executeQuery(query);
            ResultSetMetaData md = rs.getMetaData();
            int cols = 6;
            String[][] data = new String[rows][cols];
            for(int r = 0; r < rows; r++){
                for(int c = 0; c < cols ; c++ ){
                    data[r][c] = DBConnection.getDb().getAllRecords().get(r).getDataArr()[c];
                }
                r++;
            }

            return (Object[][]) data;
//            return null;
        } catch (SQLException sqle)
        {
            sqle.printStackTrace();
        }
        System.out.println("oopsie! didn't return :P");
        return null;
    }

}
