package me.mawood.data_api.sqlAbstraction;

import me.mawood.data_api.sqlAbstraction.SQLDataAccessor;

import java.io.*;
import java.sql.SQLException;
import java.util.Properties;

/**
 * data_api
 * Created by Matthew Wood on 09/09/2017.
 */
public class SQLDataAccessorFactory
{
    private static SQLDataAccessor sql = null;
    private static String hostname;
    private static int port;
    private static String database;
    private static String username;
    private static String password;
    static
    {
        Properties prop = new Properties();
        try
        {
            InputStream input = new FileInputStream("sql.config");
            prop.load(input);

        } catch (IOException e)
        {
            System.err.println("Failed to open config file");
            System.exit(-1);

        }
        hostname = prop.getProperty("hostname");
        port = Integer.parseInt(prop.getProperty("port"));
        database = prop.getProperty("database");
        username = prop.getProperty("username");
        password = prop.getProperty("password");
    }

    public static SQLDataAccessor getInstance()
    {
        if(sql == null) try
        {
            sql = new SQLDataAccessor(hostname,port,database,username,password);
        } catch (ClassNotFoundException | SQLException e)
        {
            System.err.println("Could not connect to database error:");
            System.err.println(e.getMessage());
            System.exit(-1);
        }
        return sql;
    }
}
