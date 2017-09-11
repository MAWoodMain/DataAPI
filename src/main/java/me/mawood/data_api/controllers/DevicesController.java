package me.mawood.data_api.controllers;

import me.mawood.data_api.objects.Device;
import me.mawood.data_api.objects.Reading;
import me.mawood.data_api.objects.Response;
import me.mawood.data_api.sqlAbstraction.SQLDataAccessor;
import me.mawood.data_api.sqlAbstraction.SQLDataAccessorFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.Collection;

/**
 * data_api
 * Created by Matthew Wood on 09/09/2017.
 */

@RestController
@RequestMapping("/devices")
public class DevicesController
{
    private static final SQLDataAccessor sql = SQLDataAccessorFactory.getInstance();
    private static final Log logger = LogFactory.getLog(DevicesController.class);


    @RequestMapping(value="/", method = RequestMethod.GET)
    public Response get(HttpServletResponse response)
    {
        logger.info("Called: GET /devices/");
        try
        {
            response.setStatus(HttpServletResponse.SC_OK);
            return new Response<>(sql.getDevices());
        } catch (SQLException e)
        {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            logger.error(e);
            return new Response(false, "SQL error");
        }
    }

    @RequestMapping(value="/", method = {RequestMethod.PUT, RequestMethod.POST})
    public Response insert(@RequestBody Device device, HttpServletResponse response)
    {
        logger.info("Called: PUT/POST /devices/");
        try
        {
            response.setStatus(HttpServletResponse.SC_OK);
            sql.addDevice(device);
            return new Response(true,"Added device successfully");
        } catch (SQLException e)
        {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            logger.error(e);
            return new Response(false, "SQL error");
        }
    }

}
