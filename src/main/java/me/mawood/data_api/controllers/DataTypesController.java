package me.mawood.data_api.controllers;

import me.mawood.data_api.objects.DataType;
import me.mawood.data_api.objects.Device;
import me.mawood.data_api.objects.Response;
import me.mawood.data_api.sqlAbstraction.SQLDataAccessor;
import me.mawood.data_api.sqlAbstraction.SQLDataAccessorFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;

/**
 * data_api
 * Created by Matthew Wood on 09/09/2017.
 */

@RestController
@RequestMapping("/datatypes")
public class DataTypesController
{
    private static final SQLDataAccessor sql = SQLDataAccessorFactory.getInstance();
    private static final Log logger = LogFactory.getLog(DataTypesController.class);

    @RequestMapping(value="/", method = RequestMethod.GET)
    public Response datatypes()
    {
        logger.info("Called: GET /datatypes/");
        try
        {
            return new Response<>(sql.getDataTypes());
        } catch (SQLException e)
        {
            System.err.println(e);
            return new Response(false, "SQL error");
        }
    }

    @RequestMapping(value="/", method = {RequestMethod.PUT, RequestMethod.POST})
    public Response insert(@RequestBody DataType dataType)
    {
        logger.info("Called: PUT/POST /devices/");
        try
        {
            sql.addDataType(dataType);
            return new Response(true,"Added data type successfully");
        } catch (SQLException e)
        {
            System.err.println(e);
            return new Response(false, "SQL error");
        }
    }
}
