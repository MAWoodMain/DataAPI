package me.mawood.data_api.sqlAbstraction.accessors;

import me.mawood.data_api.objects.DataType;
import me.mawood.data_api.objects.Device;
import me.mawood.data_api.objects.Reading;
import me.mawood.data_api.sqlAbstraction.SQLDataAccessor;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;

/**
 * data_api
 * Created by Matthew Wood on 12/09/2017.
 */
public class ReadingAccessor extends SQLDataAccessor
{

    public ReadingAccessor()
    {
        super();
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
            statement.setTimestamp(3, Timestamp.from(Instant.ofEpochMilli(start)));
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
}
