package me.mawood.data_api.sqlAbstraction;

import me.mawood.data_api.sqlAbstraction.SQLDataAccessor;

import java.sql.SQLException;

/**
 * data_api
 * Created by Matthew Wood on 09/09/2017.
 */
public class SQLDataAccessorFactory
{
    private static SQLDataAccessor sql = null;

    public static SQLDataAccessor getInstance()
    {
        if(sql == null) try
        {
            sql = new SQLDataAccessor("localhost",3306,"data","root","P@ssw0rd");
        } catch (ClassNotFoundException | SQLException e)
        {
            System.err.println("Could not connect to database error:");
            System.err.println(e.getMessage());
            System.exit(-1);
        }
        return sql;
    }
}
