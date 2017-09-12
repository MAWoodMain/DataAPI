package me.mawood.data_api.sqlAbstraction.accessors;

import me.mawood.data_api.objects.DataType;
import me.mawood.data_api.sqlAbstraction.SQLDataAccessor;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

/**
 * DataAPI - me.mawood.data_api.sqlAbstraction
 * Created by MattW at Corintech on 12/09/2017.
 */
public class DataTypeAccessor extends SQLDataAccessor
{

    public DataTypeAccessor()
    {
        super();
    }

    public Collection<DataType> getDataTypes() throws SQLException
    {
        Collection<DataType> dataTypes = new ArrayList<>();

        Statement stmt=connection.createStatement();
        ResultSet rs=stmt.executeQuery("select * from " + DATATYPE_TABLE_NAME);
        DataType dataType;
        while (rs.next())
        {
            dataType = new DataType();
            dataType.setDatatypeid(rs.getLong("id"));
            dataType.setName(rs.getString("name"));
            dataType.setTag(rs.getString("tag"));
            dataType.setSymbol(rs.getString("symbol"));
            dataType.setDescription(rs.getString("description"));
            dataTypes.add(dataType);
        }
        return dataTypes;
    }

    public DataType getDataTypeFromTag(String dataTypeTag) throws IllegalArgumentException, SQLException
    {
        PreparedStatement query = connection.prepareStatement("select * from "+DATATYPE_TABLE_NAME+" WHERE datatypes.tag LIKE ?");
        query.setString(1, dataTypeTag);
        ResultSet resultSet = query.executeQuery();
        if(resultSet.next())
        {
            DataType dataType = new DataType();
            dataType.setDatatypeid(resultSet.getLong("id"));
            dataType.setName(resultSet.getString("name"));
            dataType.setTag(resultSet.getString("tag"));
            dataType.setSymbol(resultSet.getString("symbol"));
            dataType.setDescription(resultSet.getString("description"));
            return dataType;
        }
        throw new IllegalArgumentException("Unknown data type tag '" + dataTypeTag + "'");
    }

    public void addDataType(DataType dataType) throws SQLException
    {
        if(!dataType.isValid()) throw new IllegalArgumentException("Invalid device parameters");
        PreparedStatement ps = connection.prepareStatement("INSERT INTO " + DATATYPE_TABLE_NAME + "(name, tag, symbol, description) VALUES (?,?,?,?)");
        ps.setString(1,dataType.getName());
        ps.setString(2,dataType.getTag());
        ps.setString(3,dataType.getSymbol());
        ps.setString(4,dataType.getDescription());
        ps.execute();
    }
}
