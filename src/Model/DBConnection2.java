package Model;

import java.sql.*;
import java.sql.DriverManager;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Properties;

public class DBConnection2 {

    public DBConnection2(String[] args){
        go(args);
    }
    /**
     * TODO List:
     * Change Creation of tables and PS to use GBD's 'record' format    --DONE BUT REVERSED. OBJECTS BE DAMMED!!!
     * Create "Insert" functions for both tables                        --DONE
     * Create "Search" functions for both tables
     * Create "Update" functions for both tables
     * Create "Delete" functions for both tables
     * Create "Get List" functions for both tables
     */
    public String driver = "org.apache.derby.jdbc.EmbeddedDriver";
    private String framework = "embedded";
    private String protocol = "jdbc:derby:";

    Connection conn;
    ArrayList<Statement> statements;
    PreparedStatement psCostInsert, psCategoryInsert;
    PreparedStatement psCostUpdate, psCategoryUpdate;
    ResultSet rs;
    Statement s;

    // Connects to the DB upon startup, and creates a DB if there is no
    //existing one
    public void go(String[] args){
        parseArguments(args);
        System.out.println("Application starting in " + framework + " mode");
        Connection conn = null;
        statements = new ArrayList<Statement>();
        Statement s;
        ResultSet rs = null;

        try {
            Properties props = new Properties(); // connection properties

            // providing a username and password is optional in the embedded
            // and derbyclient frameworks
            //props.put("user", "user1");
            //props.put("password", "user1");

            String dbName = "derbyDB"; // the name of the database
            try {
                 Class.forName(driver);
            } catch(java.lang.ClassNotFoundException e) {
                System.out.println("uh-oh!");
            }
            /*
             * This connection specifies create=true in the connection URL to
             * cause the database to be created when connecting for the first
             * time. To remove the database, remove the directory derbyDB (the
             * same as the database name) and its contents.
             *
             * The directory derbyDB will be created under the directory that
             * the system property derby.system.home points to, or the current
             * directory (user.dir) if derby.system.home is not set.
             */
            conn = DriverManager.getConnection(protocol +  dbName
                    + ";create=true", props);

            System.out.println("Connected to and created database " + dbName);
            // We want to control transactions manually. Autocommit is on by
            // default in JDBC.
            conn.setAutoCommit(false);

            /* Creating a statement object that we can use for running various
             * SQL statements commands against the database.*/
            s = conn.createStatement();
            statements.add(s);
//            deleteTables(s);
            try {

                String costTableSQL = "CREATE TABLE " +
                        "Categories(Id INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY(Start with 1, Increment by 1), "
                        +
                        "name varchar(40))";
                s.execute(costTableSQL);
                System.out.println("Created table Categories");

                String createCategoriesTableSQL = "CREATE TABLE Cost(Id INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY(Start with 1, Increment by 1), " +
                        "costSum double, " +
                        "currency varchar(20), " +
                        "category INTEGER REFERENCES Categories (id) NOT NULL , " +
                        "description varchar(60), " +
                        "costDate Date)";
                s.execute(createCategoriesTableSQL);
                System.out.println("Created table Cost");
            }catch(SQLException e){
                System.out.println(e.getMessage());
                if(e.getSQLState() == "X0Y32"){
                    System.out.println("Tables already exist! Moving on...");
                    return;
                }
            }

            //creating prepared statement for Cost and Category tables:
            psCostInsert = conn.prepareStatement(
                    "insert into Cost values (?,?,?,?,?,?)");
            statements.add(psCostInsert);

            psCategoryInsert = conn.prepareStatement(
                    "insert into Categories (name) values (?)");
            statements.add(psCategoryInsert);


            // Creating prepared statement for updating the Cost and Category tables:
            psCostUpdate = conn.prepareStatement(
                    "update Cost set id = ?,costSum = ?, currency = ?, category = ?, description = ?, costDate = ? where costDate = ?");
            statements.add(psCostUpdate);

            psCategoryUpdate = conn.prepareStatement(
                    "update Categories set cat=?, id=? where id=?");
            statements.add(psCategoryUpdate);

            // committing the creation of the tables to the Database
            conn.commit();
        }
        catch (SQLException sqle)
        {
            System.out.println(sqle.toString());
            printSQLException(sqle);
        }
    }
//
//    public void deleteTables(Statement s){
//        try {
//            s.execute("drop table Cost ");
//            System.out.println("COST table deleted");
//            s.execute("drop table Categories ");
//            System.out.println("CATEGORIES table deleted");
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//
//
//
//    }
//
//    //Insert a cost record into the Cost table
//    public void insertCost(Cost rec){
//        try{
//            psCostInsert.setInt(1,rec.getId());
//            psCostInsert.setDouble(2,rec.getSum());
//            psCostInsert.setString(3,rec.getCurrency());
//            psCostInsert.setString(4,rec.getCategory().getName());
//            psCostInsert.setString(5,rec.getDescription());
//            psCostInsert.setDate(6,Date.valueOf(rec.getDate()));
//            psCostInsert.executeUpdate();
//            System.out.println("Inserted Record");
//        } catch (SQLException sqle)
//        {
//            printSQLException(sqle);
//        }
//    }

    //Insert a category into the Category table
    public void insertCategory(Category cat){
        try{
            psCategoryInsert.setString(2, cat.getName());
//            psCategoryInsert.setInt(1,cat.getId());
//            psCategoryInsert.setObject(2,cat);

            psCategoryInsert.executeUpdate();
            System.out.println("Inserted Category");
        } catch (SQLException sqle)
        {
            sqle.printStackTrace();
            printSQLException(sqle);
        }
    }

    /**
     *
     * @param year
     */
    //Prints a list of records in a specified year
    void printList(int year){
        try{
            rs = s.executeQuery(
                    "SELECT id,costSum,currency,category,description,costDate FROM Costs ORDER BY costDate ");
            while(rs.next()){
                if(rs.getDate(6).toLocalDate().getYear() == year){
                    // print this one / use it!
                }
            }
        } catch (SQLException sqle)
        {
            printSQLException(sqle);
        }
    }

    /**
     *
     * @param searchYear
     * @param searchMonth
     */
    //Prints a list of records in a specified month and year
    void printList(int searchYear, int searchMonth){
        try{
           rs = s.executeQuery(
                   "SELECT id,costSum,currency,category,description,costDate FROM Costs WHERE YEAR(costDate) = searchYear AND MONTH(costDate) = searchMonth AND ORDER BY costDate");
           /*
           while(rs.next()){
               if(rs.getDate(6).toLocalDate().getMonthValue() == month
                       && rs.getDate(6).toLocalDate().getYear() == year){
                   // print this one / use it!
               }
           }
           */
        } catch (SQLException sqle)
        {
            printSQLException(sqle);
        }
    }

    void printCat(){
        try {
            rs = s.executeQuery("SELECT cat,id FROM Categories ORDER BY id");
            while (rs.next()){
                //decide what to do here!
            }
        } catch (SQLException sqle){
            printSQLException(sqle);
        }
    }

    //Shuts down the DB connection and all open resources. To be used when closing the application
    void shutdown(){
        if (framework.equals("embedded"))
        {
            try
            {
                // the shutdown=true attribute shuts down Derby
                DriverManager.getConnection("jdbc:derby:;shutdown=true");

                // To shut down a specific database only, but keep the
                // engine running (for example for connecting to other
                // databases), specify a database in the connection URL:
                //DriverManager.getConnection("jdbc:derby:" + dbName + ";shutdown=true");
            }
            catch (SQLException se)
            {
                if (( (se.getErrorCode() == 50000)
                        && ("XJ015".equals(se.getSQLState()) ))) {
                    // we got the expected exception
                    System.out.println("Derby shut down normally");
                    // Note that for single database shutdown, the expected
                    // SQL state is "08006", and the error code is 45000.
                } else {
                    // if the error code or SQLState is different, we have
                    // an unexpected exception (shutdown failed)
                    System.err.println("Derby did not shut down normally");
                    printSQLException(se);
                }
            }
        }

        // release all open resources to avoid unnecessary memory usage

        // ResultSet
        try {
            if (rs != null) {
                rs.close();
                rs = null;
            }
        } catch (SQLException sqle) {
            printSQLException(sqle);
        }

        // Statements and PreparedStatements
        int i = 0;
        while (!statements.isEmpty()) {
            // PreparedStatement extend Statement
            Statement st = (Statement)statements.remove(i);
            try {
                if (st != null) {
                    st.close();
                    st = null;
                }
            } catch (SQLException sqle) {
                printSQLException(sqle);
            }
        }

        //Connection
        try {
            if (conn != null) {
                conn.close();
                conn = null;
            }
        } catch (SQLException sqle) {
            printSQLException(sqle);
        }
    }

    private void reportFailure(String message) {
        System.err.println("\nData verification failed:");
        System.err.println('\t' + message);
    }

    public static void printSQLException(SQLException e)
    {
        // Unwraps the entire exception chain to unveil the real cause of the
        // Exception.
        while (e != null)
        {
            System.err.println("\n----- SQLException -----");
            System.err.println("  SQL State:  " + e.getSQLState());
            System.err.println("  Error Code: " + e.getErrorCode());
            System.err.println("  Message:    " + e.getMessage());
            // for stack traces, refer to derby.log or uncomment this:
            //e.printStackTrace(System.err);
            e = e.getNextException();
        }
    }

    private void parseArguments(String[] args)
    {
        if (args.length > 0) {
            if (args[0].equalsIgnoreCase("derbyclient"))
            {
                framework = "derbyclient";
                protocol = "jdbc:derby://localhost:1527/";
            }
        }
    }

}

