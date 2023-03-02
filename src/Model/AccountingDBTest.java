package Model;

import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;

public class AccountingDBTest {
    private String framework = "embedded";
    private String protocol = "jdbc:derby:";

    void go(String[] args) {
        parseArguments(args);

        System.out.println("SimpleApp starting in " + framework + " mode");

        Connection conn = null;
        ArrayList<Statement> statements = new ArrayList<Statement>();
        PreparedStatement psCostInsert, psCategoryInsert;
        PreparedStatement psCostUpdate, psCategoryUpdate;
        Statement s;
        ResultSet rs = null;

        try {
            Properties props = new Properties(); // connection properties
            // providing a username and password is optional in the embedded
            // and derbyclient frameworks
            props.put("user", "user1");
            props.put("password", "user1");

            String dbName = "derbyDB"; // the name of the database

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
            conn = DriverManager.getConnection(protocol + dbName
                    + ";create=true", props);

            System.out.println("Connected to and created database " + dbName);

            // We want to control transactions manually. Autocommit is on by
            // default in JDBC.
            conn.setAutoCommit(false);

            /* Creating a statement object that we can use for running various
             * SQL statements commands against the database.*/
            s = conn.createStatement();
            statements.add(s);

            // We create a table...
            s.execute("create table Cost(cat Category, price int,curr Currency, description varchar(60),date Timestamp)");
            System.out.println("Created table Cost");

            s.execute("create table Categories(cat Category, id int)");
            System.out.println("Created table Cost");

            //creating prepared statement for Cost and Category tables:
            psCostInsert = conn.prepareStatement(
                    "insert into Cost values (?, ?, ?, ?, ?)");
            statements.add(psCostInsert);

            psCategoryInsert = conn.prepareStatement(
                    "insert into Category values (?, ?)");
            statements.add(psCategoryInsert);


            // Creating prepared statement for updating the Cost and Category tables:
            psCostUpdate = conn.prepareStatement(
                    "update Cost set cat=?, price=?, curr=?, description=?, date=?, where num=?");
            statements.add(psCostUpdate);

            psCategoryUpdate = conn.prepareStatement(
                    "update Category set cat=?, id=?, where num=?");
            statements.add(psCategoryUpdate);

            conn.commit();
        }
        catch (Exception E) {

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
