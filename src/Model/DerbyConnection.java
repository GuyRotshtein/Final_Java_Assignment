package Model;

import View.MainFrame;
import Model.Cost;

import javax.swing.plaf.nimbus.State;
import java.sql.*;

import java.time.LocalDate;
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
    private PreparedStatement Psselectid;



//    private MainFrame gui;

    public DerbyConnection(){
        createConnection();
        prepareDB();
    }

    public void createConnection(){
        Properties props = new Properties(); // connection properties
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(protocol +  DBNAME
                    + ";create=true", props);
            System.out.println("Connected to and created database " + DBNAME);
//            conn.setAutoCommit(false);

        } catch(Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

    }

    public void prepareDB(){
        try {
//            deleteTables();
            createCategories();
            createCostTable();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
    public void deleteTables() throws Exception{
        sqlCmd = conn.createStatement();
        sqlCmd.execute("drop table Cost ");
        System.out.println("COST table deleted");
        sqlCmd.execute("drop table Categories ");
        System.out.println("CATEGORIES table deleted");
        sqlCmd.close();
    }


/*Catagory secion*/
    public void createCategories() {
        try {
            sqlCmd = conn.createStatement();
            String costTableSQL = "" +
                    "CREATE TABLE Categories" +
                    "(Id INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY(Start with 1, Increment by 1), " +
                    "Name varchar(40))";
            sqlCmd.execute(costTableSQL);
            sqlCmd.close();
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

    public ArrayList<Category> getAllCategories(){
        ArrayList<Category> result = new ArrayList<>();
        try {
            sqlCmd = conn.createStatement();
//            System.out.println("Printing all categories");
            String test = "SELECT * FROM Categories";
            ResultSet results = sqlCmd.executeQuery(test);
            while (results.next()){
                result.add(new Category(
                        results.getInt(1),
                        results.getString(2)
                ));
            }
            results.close();
            sqlCmd.close();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    public void insertCategory(Category cat){
        try{
            psCategoryInsert = conn.prepareStatement(
                    "insert into Categories (Name) values (?)");
            psCategoryInsert.setString(1, cat.getName());
            psCategoryInsert.executeUpdate();
            System.out.println("Inserted Category " + cat);
            psCategoryInsert.close();
        } catch (Exception sqle)
        {
            sqle.printStackTrace();
        }
    }

    public Category getCategoryByID(int id){
        Category result = null;
        try {
            sqlCmd = conn.createStatement();

            PreparedStatement getCategoryPs = conn.prepareStatement(
                    "SELECT * FROM Categories WHERE Id = (?)");
            getCategoryPs.setInt(1, id);
            ResultSet resultSet = getCategoryPs.executeQuery();
            while (resultSet.next()){
                result = new Category(resultSet.getInt(1), resultSet.getString(2));
            }
            resultSet.close();
            getCategoryPs.close();
        }catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    public int getCategoryID(Category cat ){
        int catID = -1;
        try{
            sqlCmd = conn.createStatement();
            String sql = "SELECT * FROM Categories WHERE Name = '" + cat.getName() + "'";
            ResultSet result = sqlCmd.executeQuery(sql);
            while (result.next()) catID = result.getInt(1);
            result.close();
            sqlCmd.close();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return catID;
    }



    public boolean checkCategory(String catName)
    {
        boolean res;
        try
        {
            String query = "SELECT * FROM Category WHERE Name=" + catName;
            ResultSet rs = sqlCmd.executeQuery(query);
            return rs != null;
//            if(rs==null)
//            {
//                res=false;
//            }else
//            {
//                res=true;
//            }
        } catch (SQLException sqle)
        {

            sqle.printStackTrace();
//            printSQLException(sqle);
            res=false;

        }
        return res;
    }
/*cost section*/
    public void createCostTable() {
        try{
            sqlCmd = conn.createStatement();
            String createCategoriesTableSQL = "" +
                    "CREATE TABLE Cost" +
                    "(Id INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY(Start with 1, Increment by 1), " +
                    "costSum double, " +
                    "currency varchar(20), " +
                    "category INTEGER REFERENCES Categories (id) NOT NULL , " +
                    "description varchar(60), " +
                    "costDate Date)";
            sqlCmd.execute(createCategoriesTableSQL);
            sqlCmd.close();
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

    public ArrayList<Cost> getAllCosts(){
        ArrayList<Cost> allCosts = new ArrayList<>();
        try {
            sqlCmd = conn.createStatement();
//            System.out.println("Printing all categories");
            String test = "SELECT * FROM Cost";
            ResultSet results = sqlCmd.executeQuery(test);

            while (results.next()){
                allCosts.add(new Cost(
                        results.getInt(1),
                        results.getDouble(2),
                        results.getString(3),
                        getCategoryByID(results.getInt(4)),
                        results.getString(5),
                        results.getDate(6)
                ));

            }
            results.close();
            sqlCmd.close();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return allCosts;
    }




    /*
    TODO - switch insertCost to recieve a Record, use the record to input the data
     */
    public void insertCost(Cost c){
        try{
            int catID = getCategoryID(c.getCategory());
            psCostInsert = conn.prepareStatement(
                        "insert into Cost " +
                            "(costSum,currency,category, description, costDate)" +
                            "values (?,?,?,?,?)");

            psCostInsert.setDouble(1,c.getSum());
            psCostInsert.setString(2,c.getCurrency());
            psCostInsert.setInt(3,catID);
            psCostInsert.setString(4,c.getDescription());
            psCostInsert.setDate(5,c.getDate()); // check date class
            psCostInsert.executeUpdate();
            System.out.println("Inserted Record\t" + c);
            psCostInsert.close();
        } catch (SQLException sqle)
        {
            sqle.printStackTrace();
        }
    }
    private void getCostData(Statement s)
    {
        try
        {
            //System.out.println("we getCostdata in void ");
            String rowQuery = "SELECT COUNT (*) FROM Cost";
            System.out.println(rowQuery);
            ResultSet rs = s.executeQuery(rowQuery);
            while(rs.next())
            {
                System.out.println("id" + rs.getInt("Id"));
                System.out.println("description"+ rs.getString("description"));
                System.out.println("sum"+ rs.getString("costSum"));
                System.out.println("currency" + rs.getString("currency"));
                System.out.println("category" + rs.getInt("category"));

            }
        }
        catch(SQLException e)
        {
            throw new RuntimeException(e);
        }

    }

    public ArrayList<Cost> searchCosts(int year){
        String searchYear = String.valueOf(year);
        ArrayList<Cost> searchCosts = new ArrayList<>();
        try {
            sqlCmd = conn.createStatement();
            //System.out.println("Printing all categories");
            String test = "SELECT * FROM Cost WHERE YEAR(costDate)=" + searchYear;
            ResultSet results = sqlCmd.executeQuery(test);

            while (results.next()){
                searchCosts.add(new Cost(
                        results.getInt(1),
                        results.getDouble(2),
                        results.getString(3),
                        getCategoryByID(results.getInt(4)),
                        results.getString(5),
                        results.getDate(6)
                ));

            }
            results.close();
            sqlCmd.close();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return searchCosts;
    }

    public ArrayList<Cost> searchCosts(int year, int month){
        String searchYear = String.valueOf(year);
        String searchMonth = String.valueOf(month);
        ArrayList<Cost> searchCosts = new ArrayList<>();
        try {
            sqlCmd = conn.createStatement();
            //System.out.println("Printing all categories");
            String test = "SELECT * FROM Cost WHERE YEAR(costDate)=" + searchYear + " AND MONTH(costDate)=" +searchMonth;
            ResultSet results = sqlCmd.executeQuery(test);

            while (results.next()){
                searchCosts.add(new Cost(
                        results.getInt(1),
                        results.getDouble(2),
                        results.getString(3),
                        getCategoryByID(results.getInt(4)),
                        results.getString(5),
                        results.getDate(6)
                ));

            }
            results.close();
            sqlCmd.close();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return searchCosts;
    }

    public void removeCost(int id){
        String query = "DELETE  FROM Cost WHERE Id="+id;
        try{
            sqlCmd = conn.createStatement();
            sqlCmd.execute(query);

        }
        catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    /*
        catch (SQLException sqle)
        {
            sqle.printStackTrace();
        }
    */

            /*

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
                for(int c = 0; c < cols; c++){
                    data[r][c] = rs.getString(c);
                }
            }
            return (Object[][]) data;
        } catch (SQLException sqle)
        {
            sqle.printStackTrace();
        }
        System.out.println("oopsie! didn't return :P");
        return null;
    }

             */

            /*
    public void insertCost(Record newRecord){
        try{
            psCostInsert = conn.prepareStatement("insert  into " )
            psCategoryInsert.setString(1, catStr);
            psCategoryInsert.executeUpdate();
            System.out.println("Inserted Category");
        } catch (SQLException sqle)
        {
            sqle.printStackTrace();
//            printSQLException(sqle);
        }
    }
    */

    /*
    public string[][] get_cost(Statement s)
    {


    }
    */

}

/*
    - Create a method to return all Cost
    - Parse all expenses to 2d string array
    - Return string array to viewmodel -> insert into the gui
    - Managing categories - create a layout with 3/4 fields -> catName,
    - lookup datamodels for tables
    Add new -> ___________  add
    ID  Name
    1   Clothes     Edit       Delete



 */