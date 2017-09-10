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
    private static final String DEVICE_TABLE_NAME = "devices";
    private static final String DATATYPE_TABLE_NAME = "datatypes";
    private static final String READING_TABLE_NAME = "readings";

    private final Connection connection;
    public SQLDataAccessor(String hostname, int port, String dbName, String username, String password) throws ClassNotFoundException, SQLException
    {
        Class.forName("com.mysql.jdbc.Driver");
        connection = DriverManager.getConnection(
                "jdbc:mysql://" + hostname + ":" + port + "/" + dbName,username,password);
    }

    public Collection<Device> getDevices() throws SQLException
    {
        Collection<Device> devices = new ArrayList<>();

        Statement stmt=connection.createStatement();
        ResultSet rs=stmt.executeQuery("select * from " + DEVICE_TABLE_NAME);
        Device device;
        while (rs.next())
        {
            device = new Device();
            device.setId(rs.getLong("id"));
            device.setParentId(rs.getLong("parentId"));
            device.setName(rs.getString("name"));
            device.setTag(rs.getString("tag"));
            device.setDescription(rs.getString("description"));
            devices.add(device);
        }
        return devices;
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


    public Device getDeviceFromTag(String locationTag) throws IllegalArgumentException, SQLException
    {
        PreparedStatement query = connection.prepareStatement("select * from "+ DEVICE_TABLE_NAME +" WHERE devices.tag LIKE ?");
        query.setString(1, locationTag);
        ResultSet resultSet = query.executeQuery();
        if(resultSet.next())
        {
            Device device = new Device();
            device.setId(resultSet.getLong("id"));
            device.setParentId(resultSet.getLong("parentId"));
            device.setName(resultSet.getString("name"));
            device.setTag(resultSet.getString("tag"));
            device.setDescription(resultSet.getString("description"));
            return device;
        }
        throw new IllegalArgumentException("Unknown device tag '" + locationTag + "'");
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

    public Collection<Reading> getReadingsFor(Device device, DataType dataType, Long start, Long end) throws SQLException
    {
        String query = "select * from "+READING_TABLE_NAME+" WHERE (readings.deviceId LIKE ?) AND (readings.dataTypeId LIKE ?)";
        if(start != null) query += " AND (readings.timestamp >= ?)";
        if(end != null) query += " AND (readings.timestamp <= ?)";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setLong(1, device.getId());
        statement.setLong(2, dataType.getDatatypeid());
        if(start != null)
        {
            statement.setTimestamp(3,Timestamp.from(Instant.ofEpochMilli(start)));
            if(end != null) statement.setTimestamp(4,Timestamp.from(Instant.ofEpochMilli(end)));
        } else
        {
            if(end != null) statement.setTimestamp(3,Timestamp.from(Instant.ofEpochMilli(end)));
        }
        ResultSet resultSet = statement.executeQuery();
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

    public int insertReadings(Device device, DataType dataType, Collection<Reading> readings) throws SQLException
    {
        PreparedStatement ps = connection.prepareStatement("INSERT INTO " + READING_TABLE_NAME + "(deviceId, dataTypeId, reading, timestamp) VALUES (?,?,?,?)");
        for(Reading r:readings)
        {
            ps.clearParameters();
            ps.setLong(1, device.getId());
            ps.setLong(2,dataType.getDatatypeid());
            ps.setDouble(3,r.getReading());
            ps.setTimestamp(4,r.getTimestamp());
            ps.addBatch();
        }
        int count = 0;
        for(int i:ps.executeBatch()) count+=i;
        return count;
    }

    public void addDevice(Device device) throws SQLException
    {
        if(!device.isValid()) throw new IllegalArgumentException("Invalid device parameters");
        PreparedStatement ps = connection.prepareStatement("INSERT INTO " + DEVICE_TABLE_NAME + "(name, tag, description) VALUES (?,?,?)");
        ps.setString(1,device.getName());
        ps.setString(2,device.getTag());
        ps.setString(3,device.getDescription());
        ps.execute();
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
