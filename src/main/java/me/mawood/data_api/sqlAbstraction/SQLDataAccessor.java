package me.mawood.data_api.sqlAbstraction;

import java.sql.*;

/**
 * data_api
 * Created by Matthew Wood on 09/09/2017.
 */
public class SQLDataAccessor
{
    protected static final String DEVICE_TABLE_NAME = "devices";
    protected static final String DATATYPE_TABLE_NAME = "datatypes";
    protected static final String READING_TABLE_NAME = "readings";

    protected final Connection connection;

    protected SQLDataAccessor()
    {
        connection = ConnectionFactory.getInstance();
    }
}
