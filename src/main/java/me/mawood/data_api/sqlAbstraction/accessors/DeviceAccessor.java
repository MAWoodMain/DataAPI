package me.mawood.data_api.sqlAbstraction.accessors;

import me.mawood.data_api.objects.Device;
import me.mawood.data_api.sqlAbstraction.SQLDataAccessor;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

/**
 * data_api
 * Created by Matthew Wood on 12/09/2017.
 */
public class DeviceAccessor extends SQLDataAccessor
{
    public DeviceAccessor()
    {
        super();
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

    public void addDevice(Device device) throws SQLException
    {
        if(!device.isValid()) throw new IllegalArgumentException("Invalid device parameters");
        PreparedStatement ps = connection.prepareStatement("INSERT INTO " + DEVICE_TABLE_NAME + "(name, tag, description) VALUES (?,?,?)");
        ps.setString(1,device.getName());
        ps.setString(2,device.getTag());
        ps.setString(3,device.getDescription());
        ps.execute();
    }
}
