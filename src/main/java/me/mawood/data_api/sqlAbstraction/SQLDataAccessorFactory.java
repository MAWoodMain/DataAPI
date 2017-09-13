package me.mawood.data_api.sqlAbstraction;

import me.mawood.data_api.sqlAbstraction.SQLDataAccessor;
import me.mawood.data_api.sqlAbstraction.accessors.DataTypeAccessor;
import me.mawood.data_api.sqlAbstraction.accessors.DeviceAccessor;
import me.mawood.data_api.sqlAbstraction.accessors.ReadingAccessor;

import java.util.HashMap;
import java.util.Map;

/**
 * data_api
 * Created by Matthew Wood on 09/09/2017.
 */
public class SQLDataAccessorFactory
{
    private static SQLDataAccessor sql = null;

    private static final Map<AccessorType,SQLDataAccessor> accessors = new HashMap<>();


    public static SQLDataAccessor getInstance(AccessorType type)
    {
        if(!accessors.containsKey(type)) create(type);
        return accessors.get(type);

    }

    private static void create(AccessorType type)
    {
        if(accessors.containsKey(type)) return;

        switch (type)
        {
            case DATA_TYPE:
                accessors.put(type,new DataTypeAccessor());
                break;
            case DEVICE:
                accessors.put(type,new DeviceAccessor());
                break;
            case READING:
                accessors.put(type,new ReadingAccessor());
                break;
            default:
                throw new UnsupportedOperationException("Unsupported accessor type '" + type.name() + "'");
        }
    }
}
