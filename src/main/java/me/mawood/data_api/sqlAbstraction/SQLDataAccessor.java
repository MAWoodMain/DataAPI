package me.mawood.data_api.sqlAbstraction;

import me.mawood.data_api.objects.DataType;
import me.mawood.data_api.objects.Location;
import me.mawood.data_api.objects.Reading;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

/**
 * data_api
 * Created by Matthew Wood on 09/09/2017.
 */
public class SQLDataAccessor
{
    private static final String LOCATION_TABLE_NAME = "location";
    private static final String DATATYPE_TABLE_NAME = "datatype";
    private static final String READING_TABLE_NAME = "readings";

    private final Connection connection;
    public SQLDataAccessor(String hostname, int port, String dbName, String username, String password) throws ClassNotFoundException, SQLException
    {
        Class.forName("com.mysql.jdbc.Driver");
        connection = DriverManager.getConnection(
                "jdbc:mysql://" + hostname + ":" + port + "/" + dbName,username,password);
    }

    public Collection<Location> getLocations() throws SQLException
    {
        Collection<Location> locations = new ArrayList<>();

        Statement stmt=connection.createStatement();
        ResultSet rs=stmt.executeQuery("select * from " + LOCATION_TABLE_NAME);
        Location location;
        while (rs.next())
        {
            location = new Location();
            location.setLocationid(rs.getLong("locationid"));
            location.setParentlocation(rs.getLong("parentlocation"));
            location.setName(rs.getString("name"));
            location.setTag(rs.getString("tag"));
            location.setDescription(rs.getString("description"));
            locations.add(location);
        }
        return locations;
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
            dataType.setDatatypeid(rs.getLong("datatypeid"));
            dataType.setName(rs.getString("name"));
            dataType.setTag(rs.getString("tag"));
            dataType.setSymbol(rs.getString("symbol"));
            dataType.setDescription(rs.getString("description"));
            dataTypes.add(dataType);
        }
        return dataTypes;
    }


    public Location getLocationFromTag(String locationTag) throws IllegalArgumentException, SQLException
    {
        PreparedStatement query = connection.prepareStatement("select * from "+LOCATION_TABLE_NAME+" WHERE location.tag LIKE ?");
        query.setString(1, locationTag);
        ResultSet resultSet = query.executeQuery();
        if(resultSet.next())
        {
            Location location = new Location();
            location.setLocationid(resultSet.getLong("locationid"));
            location.setParentlocation(resultSet.getLong("parentlocation"));
            location.setName(resultSet.getString("name"));
            location.setTag(resultSet.getString("tag"));
            location.setDescription(resultSet.getString("description"));
            return location;
        }
        throw new IllegalArgumentException("Unknown tag '" + locationTag + "'");
    }

    public DataType getDataTypeFromTag(String dataTypeTag) throws IllegalArgumentException, SQLException
    {
        PreparedStatement query = connection.prepareStatement("select * from "+DATATYPE_TABLE_NAME+" WHERE datatype.tag LIKE ?");
        query.setString(1, dataTypeTag);
        ResultSet resultSet = query.executeQuery();
        if(resultSet.next())
        {
            DataType dataType = new DataType();
            dataType.setDatatypeid(resultSet.getLong("datatypeid"));
            dataType.setName(resultSet.getString("name"));
            dataType.setTag(resultSet.getString("tag"));
            dataType.setSymbol(resultSet.getString("symbol"));
            dataType.setDescription(resultSet.getString("description"));
            return dataType;
        }
        throw new IllegalArgumentException("Unknown tag '" + dataTypeTag + "'");
    }

    public Collection<Reading> getReadingsFor(Location location, DataType dataType, Long start, Long end) throws SQLException
    {
        PreparedStatement query = connection.prepareStatement("select * from "+READING_TABLE_NAME+" WHERE readings.locationid LIKE ? AND readings.datatypeid LIKE ?");
        query.setLong(1, location.getLocationid());
        query.setLong(2, dataType.getDatatypeid());
        ResultSet resultSet = query.executeQuery();
        Collection<Reading> results = new ArrayList<>();
        Reading reading;
        while (resultSet.next())
        {
            reading = new Reading();
            reading.setReading(resultSet.getDouble("reading"));
            reading.setTimestamp(resultSet.getTimestamp("timestamp"));
            results.add(reading);
        }
        return results;
    }

    public int insertReadings(Location location, DataType dataType, Collection<Reading> readings) throws SQLException
    {
        PreparedStatement ps = connection.prepareStatement("INSERT INTO " + READING_TABLE_NAME + "(locationid, datatypeid, reading, timestamp) VALUES (?,?,?,?)");
        for(Reading r:readings)
        {
            ps.clearParameters();
            ps.setLong(1,location.getLocationid());
            ps.setLong(2,dataType.getDatatypeid());
            ps.setDouble(3,r.getReading());
            ps.setTimestamp(4,r.getTimestamp());
            ps.addBatch();
        }
        int count = 0;
        for(int i:ps.executeBatch()) count+=i;
        return count;
    }
}
