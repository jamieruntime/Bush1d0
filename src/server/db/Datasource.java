/**
 * BUSH1D0 ~ Jamie Purchase ~ 30/04/2016
 */
package server.db;

import app.Console;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Jamie
 */
public class Datasource
{
    private final String name, database, host, username, password;
    private Connection connection;
    
    public Datasource(String name, String database, String host, String username, String password)
    {
        this.name = name;
        this.database = database;
        this.host = host;
        this.username = username;
        this.password = password;
        this.connection = null;
    }
    
    private void connect()
    {
        try
        {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            try {this.connection = DriverManager.getConnection("jdbc:sqlserver://" + this.host + ";databaseName=" + this.database, this.username, this.password);}
            catch (SQLException ex) {Console.echo("### datasource " + this.name + " connection error: " + ex.getMessage());}
        }
        catch (ClassNotFoundException ex) {Console.echo("### datasource " + this.name + " connection error: driver not found");}
    }

    public ArrayList<HashMap<String, String>> query(String statement)
    {
        // NOTE: need to return a wrapper object that contains the array of hashmaps but also metadata
        //  (eg: datasource, execution time, statement, record count, field names?)
        // NOTE: should we return this object still if we encounter an error?

        // Force connection
        if(this.connection == null) {this.connect();}
        
        // Build results
        ResultSet result;
        try
        {
            // Execute the query and get meta data
            result = this.connection.createStatement().executeQuery(statement);
            final ResultSetMetaData meta = result.getMetaData();
            
            // Build list of columns
            ArrayList<String> field = new ArrayList();
            for(int c = 0; c < meta.getColumnCount(); c ++) {field.add(meta.getColumnName(c + 1));}
            
            // Build array of results
            final ArrayList<HashMap<String, String>> resultArray = new ArrayList();
            HashMap<String, String> resultMap = null;
            while(result.next())
            {
                resultMap = new HashMap();
                for(int f = 0; f < field.size(); f ++) {resultMap.put(field.get(f), result.getString(field.get(f)));}
                resultArray.add(resultMap);
            }
            
            // Return result array
            return resultArray;
        }
        catch (SQLException ex) {Console.echo("### datasource " + this.name + " query error: " + ex.getMessage());}
        return null;
    }

    public void queryEcho(String statement)
    {
        if(this.connection == null) {this.connect();}
        ResultSet result;
        try
        {
            result = this.connection.createStatement().executeQuery(statement);
            while(result.next()) {Console.echo(result.getString("field1"));}
        }
        catch (SQLException ex) {Console.echo("### datasource " + this.name + " query error: " + ex.getMessage());}
    }

}