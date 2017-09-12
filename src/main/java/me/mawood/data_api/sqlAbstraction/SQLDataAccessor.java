package me.mawood.data_api.sqlAbstraction;

import me.mawood.data_api.objects.DataType;
import me.mawood.data_api.objects.Device;
import me.mawood.data_api.objects.Reading;

import java.sql.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;

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

    public SQLDataAccessor()
    {
        connection = ConnectionFactory.getInstance();
    }
}
