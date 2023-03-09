package Model;

import javax.swing.plaf.nimbus.State;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.Properties;

public class DerbyConnection {

    /*Config Strings*/
    private String driver = "org.apache.derby.jdbc.EmbeddedDriver";
    private String framework = "embedded";
    private String protocol = "jdbc:derby:";
    private String DBNAME = "derbyDB";
    ////////////////////////////////////////////////

    private ArrayList<Statement> statements;
    private Statement sqlCmd;
    private Connection conn = null;
    private PreparedStatement psCategoryInsert, psCostInsert;
    private PreparedStatement psCategoryUpdate, psCostUpdate;
    private PreparedStatement psCategoryDelete, psCostDelete;



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





}
