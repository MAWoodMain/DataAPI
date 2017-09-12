package me.mawood.data_api.controllers;

import me.mawood.data_api.objects.DataType;
import me.mawood.data_api.objects.Response;
import me.mawood.data_api.sqlAbstraction.AccessorType;
import me.mawood.data_api.sqlAbstraction.SQLDataAccessorFactory;
import me.mawood.data_api.sqlAbstraction.accessors.DataTypeAccessor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;

/**
 * data_api
 * Created by Matthew Wood on 09/09/2017.
 */

@RestController
@RequestMapping("/datatypes")
public class DataTypesController
{
    private static final DataTypeAccessor dataTypeAccessor = (DataTypeAccessor)SQLDataAccessorFactory.getInstance(AccessorType.DATA_TYPE);
    private static final Log logger = LogFactory.getLog(DataTypesController.class);

    @RequestMapping(value="/", method = RequestMethod.GET)
    public Response datatypes(HttpServletResponse response)
    {
        logger.info("Called: GET /datatypes/");
        try
        {
            response.setStatus(HttpServletResponse.SC_OK);
            return new Response<>(dataTypeAccessor.getDataTypes());
        } catch (SQLException e)
        {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            logger.error(e);
            return new Response(false, "SQL error");
        }
    }

    @RequestMapping(value="/", method = {RequestMethod.PUT, RequestMethod.POST})
    public Response insert(@RequestBody DataType dataType,HttpServletResponse response)
    {
        logger.info("Called: PUT/POST /devices/");
        try
        {
            response.setStatus(HttpServletResponse.SC_OK);
            dataTypeAccessor.addDataType(dataType);
            return new Response(true,"Added data type successfully");
        } catch (SQLException e)
        {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            logger.error(e);
            return new Response(false, "SQL error");
        }
    }
}
