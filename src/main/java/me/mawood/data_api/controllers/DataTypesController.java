package me.mawood.data_api.controllers;

import io.swagger.annotations.Api;
import me.mawood.data_api.objects.DataType;
import me.mawood.data_api.objects.Response;
import me.mawood.data_api.sqlAbstraction.AccessorType;
import me.mawood.data_api.sqlAbstraction.SQLDataAccessorFactory;
import me.mawood.data_api.sqlAbstraction.accessors.DataTypeAccessor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;

/**
 * data_api
 * Created by Matthew Wood on 09/09/2017.
 */

@RestController
@Api(tags = "Data Types")
@RequestMapping("/api/datatypes")
public class DataTypesController
{
    private static final DataTypeAccessor dataTypeAccessor = (DataTypeAccessor)SQLDataAccessorFactory.getInstance(AccessorType.DATA_TYPE);
    private static final Log logger = LogFactory.getLog(DataTypesController.class);

    @RequestMapping(value="/", method = RequestMethod.GET)
    public Response datatypes(HttpServletResponse response)
    {
        logger.debug("Called: GET /datatypes/");
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
        logger.debug("Called: PUT/POST /devices/");
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

    @RequestMapping(value="/{dataTypeTag}", method = {RequestMethod.DELETE})
    public Response delete(@PathVariable String dataTypeTag, HttpServletResponse response)
    {
        logger.debug("Called: DELETE /datatypes/");
        try
        {
            DataType dataType = dataTypeAccessor.getDataTypeFromTag(dataTypeTag);
            dataTypeAccessor.delete(dataType);
            return new Response(true,"Deleted data type successfully");
        } catch (SQLException e)
        {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            logger.error(e);
            return new Response(false, "SQL error");
        } catch (IllegalArgumentException e)
        {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            logger.error(e);
            return new Response(false, e.getMessage());
        }
    }

    @RequestMapping(value="/{dataTypeTag}", method = {RequestMethod.GET})
    public Response get(@PathVariable String dataTypeTag, HttpServletResponse response)
    {
        logger.debug("Called: GET /datatypes/" + dataTypeTag);
        try
        {
            DataType device = dataTypeAccessor.getDataTypeFromTag(dataTypeTag);
            return new Response<>(device);
        } catch (SQLException e)
        {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            logger.error(e);
            return new Response(false, "SQL error");
        } catch (IllegalArgumentException e)
        {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            logger.error(e);
            return new Response(false, e.getMessage());
        }
    }
}
