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
    // Statement ID list
    private ArrayList<Statement> statements;
    // Connection to the Database
    private Connection conn = null;
    //Default statement
    private Statement sqlCmd;
    /*PreparedStatement objects*/
    private PreparedStatement psCategoryInsert, psCostInsert;
    private PreparedStatement psCategoryUpdate, psCostUpdate;
    private PreparedStatement psCategoryDelete, psCostDelete;
    private PreparedStatement Psselectid;



//    private MainFrame gui;

    /**
     * This is the Constructor. runs all the required functions for initially creating a
     * Derbydb connection to an embedded database.
     *
     */
    public DerbyConnection(){
        createConnection();
        prepareDB();
    }

    /**
     * Creating the connection to the DerbyDB embedded server. This function defines the connection properties,
     * and creates a connection to the database using them.
     */
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

    /**
     * Checking if both tables already exist, and Creating both if they don't exist already.
     */
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

    /**
     * Delete both tables and their data
     * @throws Exception
     */
    public void deleteTables() throws Exception{
        sqlCmd = conn.createStatement();
        sqlCmd.execute("drop table Cost ");
        System.out.println("COST table deleted");
        sqlCmd.execute("drop table Categories ");
        System.out.println("CATEGORIES table deleted");
        sqlCmd.close();
    }


/*Catagory secion*/

    /**
     * Creating the "CREATE" statement for the category table, and adding it to the existing statements.
     */
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

    /**
     * Contacts the database, and returns an arraylist of all categories added to the database.
     */
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

    /**
     * Recieves a Category object, and uses its details to create a statement for inserting it into the database.
     * the Category file gets passed through ViewModel, and gets created there.
     * @param cat
     */
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

    /**
     * Recievs an integer representing the ID of an existing category, and searches through the
     * database for it. Returns the found category object.
     * @param id
     * @return
     */
    public Category getCategoryByID(int id){
        Category result = null;
        try {
            sqlCmd = conn.createStatement();
            // creating a preparedStatement for searching for the Category by ID
            PreparedStatement getCategoryPs = conn.prepareStatement(
                    "SELECT * FROM Categories WHERE Id = (?)");
            getCategoryPs.setInt(1, id);
            //Sending the query to the database
            ResultSet resultSet = getCategoryPs.executeQuery();
            while (resultSet.next()){
                // receiving the Int from the 1st colum and the category String from the 2nd colum
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
    /**
     * Recievs a Category object representing the ID of an existing category, and searches through the
     * database for it. Returns the found category object.
     * @param cat
     * @return
     */
    public int getCategoryID(Category cat ){
        int catID = -1;
        try{
            sqlCmd = conn.createStatement();
            // creating a preparedStatement for searching for the Category by the given category's details
            String sql = "SELECT * FROM Categories WHERE Name = '" + cat.getName() + "'";
            //Sending the query to the database
            ResultSet result = sqlCmd.executeQuery(sql);
            // receiving the Category object from the database
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


    /**
     * Searches the database for a category based on its name, and returns
     * said category if it finds one with the same name in the database.
     * @param catName
     * @return
     */
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
    /**
     * Creating the "CREATE" statement for the Cost table, and adding it to the existing statements.
     */
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
    /**
     * Contacts the database, and returns an arraylist of all costs added to the database.
     */
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




    /**
     * Recieves a Cost object, and uses its details to create a statement for inserting it into the database.
     * the Cost file gets passed through ViewModel, and gets created there.
     * @param c
     */
    public void insertCost(Cost c){
        try{
            int catID = getCategoryID(c.getCategory());
            //creating the preparedStatement
            psCostInsert = conn.prepareStatement(
                        "insert into Cost " +
                            "(costSum,currency,category, description, costDate)" +
                            "values (?,?,?,?,?)");

            psCostInsert.setDouble(1,c.getSum());
            psCostInsert.setString(2,c.getCurrency());
            psCostInsert.setInt(3,catID);
            psCostInsert.setString(4,c.getDescription());
            psCostInsert.setDate(5,c.getDate());
            //executing the preparedStatement to insert the Cost to the database
            psCostInsert.executeUpdate();
            System.out.println("Inserted Record\t" + c);
            psCostInsert.close();
        } catch (SQLException sqle)
        {
            sqle.printStackTrace();
        }
    }

    /**
     * Returns and prints all data of Costs stored in the database.
     * @param s - given statement by model.
     */
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

    /**
     * Searches the database and returns an array of all costs that were made in the same year as a given parameter.
     *
     * @param year - parameter of search query, passed through ViewModel.
     * @return - returns an array of all costs found.
     */
    public ArrayList<Cost> searchCosts(int year){
        String searchYear = String.valueOf(year);
        ArrayList<Cost> searchCosts = new ArrayList<>();
        try {
            sqlCmd = conn.createStatement();
            //Creating the string for the search query
            String test = "SELECT * FROM Cost WHERE YEAR(costDate)=" + searchYear;
            ResultSet results = sqlCmd.executeQuery(test);
            // adding the data returned from the database into the arrayList
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
        //returning the arrayList object
        return searchCosts;
    }

    /**
     * Searches the database and returns an array of all costs that were made in the same year and month as a given parameter.
     * @param year - parameter of search query, passed through ViewModel.
     * @param month - parameter of search query, passed through ViewModel.
     * @return - returns an array of all costs found.
     */
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

    /**
     * Searches for and removes a cost based on its ID from the database
     * @param id - id of cost that needs to be removed. passed through ViewModel
     */
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
}
