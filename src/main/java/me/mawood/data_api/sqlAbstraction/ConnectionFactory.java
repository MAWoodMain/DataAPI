package me.mawood.data_api.sqlAbstraction;

import me.mawood.data_api.controllers.DevicesController;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * data_api
 * Created by Matthew Wood on 12/09/2017.
 */
class ConnectionFactory
{
    private static final Log logger = LogFactory.getLog(DevicesController.class);

    private static SQLDataAccessor sql = null;
    private static final String hostname;
    private static final int port;
    private static final String database;
    private static final String username;
    private static final String password;
    private static Connection connection;
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


    public static Connection getInstance()
    {
        if (connection == null)
        {
            try
            {
                Class.forName("com.mysql.jdbc.Driver");
                connection = DriverManager.getConnection(
                        "jdbc:mysql://" + hostname + ":" + port + "/" + database,username,password);
            } catch (SQLException | ClassNotFoundException e)
            {
                logger.error("Failed to open SQL connection.");
                logger.error(e);
                System.exit(-1);
            }
        }
        return connection;
    }
}
